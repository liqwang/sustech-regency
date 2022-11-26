package com.sustech.regency.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.component.OrderWebSocket;
import com.sustech.regency.db.dao.*;
import com.sustech.regency.db.po.*;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.model.vo.PayInfo;
import com.sustech.regency.model.vo.RoomInfo;
import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.util.AlipayUtil;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sustech.regency.db.po.OrderStatus.PAYED;
import static com.sustech.regency.db.po.OrderStatus.REFUNDED;
import static com.sustech.regency.util.VerificationUtil.getUserId;
import static com.sustech.regency.web.util.AssertUtil.asserts;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Resource
    private CommentAttachmentDao commentAttachmentDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private FileUtil fileUtil;
    @Resource
    private CollectionDao collectionDao;

    @Override
    public String uploadCommentMedia(MultipartFile media, Integer orderId) {
        checkOrderAndOwner(orderId);
        return fileUtil.uploadDisplayMedia(media, commentAttachmentDao, new CommentAttachment(null, orderId));
    }

    @Resource
    private FileDao fileDao;
    @Resource
    private RoomDao roomDao;
    @Resource
    private PublicService publicService;

    @Override
    public void deleteCommentMedia(String mediaId, Integer orderId) {
        checkOrderAndOwner(orderId);
        asserts(fileDao.selectById(mediaId) != null, "该文件不存在");
        CommentAttachment commentAttachment =
                commentAttachmentDao.selectOne(
                        new LambdaQueryWrapper<CommentAttachment>()
                                .eq(CommentAttachment::getOrderId, orderId)
                                .eq(CommentAttachment::getFileId, mediaId)
                );
        asserts(commentAttachment != null, "该文件不是该评论的展示图片或视频");
        fileUtil.deleteFile(mediaId);
    }

    private void checkOrderAndOwner(Integer orderId) {
        Order order = orderDao.selectById(orderId);
        asserts(order != null, "订单id不存在");
        asserts(order.getPayerId().equals(getUserId()), "该订单属于别人");
    }

    /**
     * Todo:先暴力加锁，后续要优化<p>
     * Todo:实现订单超时未支付，RabbitMQ延时队列 or Redis的key过期，参考:<a href="https://juejin.cn/post/7023543229337305124">1</a>
     * Todo:实现当前时间超过endTime自动转为NOT_COMMENTED状态
     */
    @Resource
    private AlipayUtil alipayUtil;
    @Resource
    private RoomTypeDao roomTypeDao;
    @Value("${alipay.pay-timeout}")
    private String payTimeout;

    @Override
    public synchronized PayInfo reserveRoom(Integer roomId, Date startDate, Date endDate) {
        asserts(endDate.after(startDate), "退房日期要在入住日期之后");
        asserts(startDate.after(new Date()), "入住日期已过");
        Room room = roomDao.selectById(roomId);
        asserts(room != null, "房间不存在");
        asserts(orderDao.selectList(
                                new LambdaQueryWrapper<Order>()
                                        .select(Order::getId) //避免回表
                                        .eq(Order::getRoomId, roomId)
                                        .lt(Order::getDateStart, endDate)
                                        .gt(Order::getDateEnd, startDate)
                                        .and(queryWrapper ->
                                                queryWrapper.eq(Order::getStatus, PAYED)
                                                        .or() //这两个状态下该房间是被占有的
                                                        .eq(Order::getStatus, OrderStatus.NOT_PAYED)))
                        .isEmpty(),
                "该房间的该时间段已经被预订");

        int days = (int) DateUtil.betweenDay(startDate, endDate, true); //计算天数
        Order order = new Order()
                .setRoomId(roomId)
                .setDateStart(startDate)
                .setDateEnd(endDate)
                .setPayerId(getUserId())
                .setFee(room.getPrice() * days * room.getDiscount());
        orderDao.insert(order);
        //提前设置交易信息
        AlipayTradePrecreateModel alipayInfo = new AlipayTradePrecreateModel();
        alipayInfo.setOutTradeNo(order.getId() + "");
        alipayInfo.setSubject(roomTypeDao.selectById(room.getTypeId()).getName() + days + "天"); //产品名称
        alipayInfo.setTotalAmount(room.getPrice() * days * room.getDiscount() + ""); //金额
        alipayInfo.setTimeoutExpress(payTimeout); //支付超时时间
        String base64QrCode = alipayUtil.getPayQrCode(alipayInfo);
        @SuppressWarnings("ConstantConditions")
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String webSocketUrl = "ws://" + request.getServerName() + ":" + request.getServerPort() + "/websocket/order/" + order.getId();
        return new PayInfo().setBase64QrCode(base64QrCode)
                .setWebSocketUrl(webSocketUrl);
    }

    @Override
    public void roomPayed(Long orderId, Date payTime) {
        orderDao.updateById(new Order()
                .setId(orderId)
                .setStatus(PAYED)
                .setPayTime(payTime));
        OrderWebSocket.notifyFrontend(orderId);
    }

    @Override
    public void cancelOrder(Long orderId) {
        //1.校验
        Order order = orderDao.selectById(orderId);
        asserts(order != null, "该订单不存在");
        asserts(order.getPayerId().equals(getUserId()), "这不是你的订单");
        asserts(order.getStatus() == PAYED, "该订单不是已付款状态");

        //2.调用支付宝API退款
        AlipayTradeRefundModel refundInfo = new AlipayTradeRefundModel();
        refundInfo.setRefundAmount(order.getFee() + "");
        refundInfo.setOutTradeNo(order.getId() + "");
        Date refundTime = alipayUtil.refund(refundInfo);

        //3.修改数据库
        order.setStatus(REFUNDED)
                .setRefundTime(refundTime);
        orderDao.updateById(order);
    }

    @Override
    public void like(Integer hotelId) {
        LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collection::getHotelId, hotelId).eq(Collection::getUserId, getUserId());
        Collection one = collectionDao.selectOne(wrapper);
        asserts(one == null, "You have liked this hotel before");
        Collection collection = Collection.builder().userId(getUserId()).hotelId(hotelId).build();
        collectionDao.insert(collection);
    }

    @Override
    public void dislike(Integer hotelId) {
        LambdaQueryWrapper<Collection> collectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        collectionLambdaQueryWrapper.eq(Collection::getHotelId, hotelId).eq(Collection::getUserId, getUserId());
        Collection collection = collectionDao.selectOne(collectionLambdaQueryWrapper);
        asserts(collection != null, "Hotel have not been liked");
        collectionDao.delete(collectionLambdaQueryWrapper);
    }

    @Override
    public List<HotelInfo> getHotelInfoFromLikes() {
        LambdaQueryWrapper<Collection> collectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        collectionLambdaQueryWrapper.eq(Collection::getUserId, getUserId());
        List<Collection> collections = collectionDao.selectList(collectionLambdaQueryWrapper);
        List<HotelInfo> likes = new ArrayList<>();
        for (Collection c :
                collections) {
            Integer hotel_id = c.getHotelId();
            HotelInfo hotel = publicService.getOneHotelByHotelId(hotel_id);
            likes.add(hotel);
        }
        return likes;
    }

    @Override
    public List<Order> getOrders() {
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getPayerId, getUserId());
        return orderDao.selectList(orderLambdaQueryWrapper);
    }

    @Override
    public List<Order> selectCustomerOrders(Boolean isComment, Date startTime, Date EndTime, Integer status) {
        List<Order> orderList = new ArrayList<>();
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();

        orderLambdaQueryWrapper.eq(Order::getPayerId, getUserId());
        List<Order> orders = orderDao.selectList(orderLambdaQueryWrapper);
        for (Order o : orders) {
            boolean judge = true;

            if (startTime != null && EndTime != null) {
                if (!(startTime.before(o.getDateEnd()) && EndTime.after(o.getDateEnd()))) {
                    judge = false;
                }
            }

            if (isComment != null) {
                if (o.getComment() == null) {
                    judge = false;
                }
            }

            if (status != null) {
                if (o.getStatus().ordinal() != status) {
                    judge = false;
                }
            }

            if (judge) {
                orderList.add(o);
            }

        }

        return orderList;
    }

    @Override
    public List<RoomInfo> getRoomInfosByCustomerChoice(Integer hotelId, Date startTime, Date EndTime, Integer minPrice, Integer maxPrice, Integer roomTypeId) {
        asserts((startTime==null&&EndTime==null)||(startTime!=null&&EndTime!=null), "StartTime and EndTime need to be chosen at the same time");
        List<Room> rooms = publicService.getRoomsByHotel(hotelId, roomTypeId);
        for (Room room :
                rooms) {
            if(minPrice!=null){
                if (!(room.getPrice() >= minPrice)) {
                    rooms.remove(room);
                }
            }

            if(maxPrice!=null){
                if (!(room.getPrice() <= maxPrice)) {
                    rooms.remove(room);
                }
            }

            LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderLambdaQueryWrapper.eq(Order::getRoomId, room.getId());
            List<Order> orders = orderDao.selectList(orderLambdaQueryWrapper);
            for (Order o : orders) {
                if (startTime!=null && EndTime!=null){
                    if (!(startTime.before(o.getDateEnd()) && EndTime.after(o.getDateEnd()))) {
                        rooms.remove(room);
                        break;
                    }
                }
            }
        }

        List<RoomInfo> roomInfoList = new ArrayList<>();
        for (Room room :
                rooms) {
            roomInfoList.add(publicService.getRoomInfoByRoomId(room.getId()));
        }
        return roomInfoList;
    }

}