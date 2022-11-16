package com.sustech.regency.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.*;
import com.sustech.regency.db.po.*;
import com.sustech.regency.model.param.Cohabitant;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.util.AlipayUtil;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sustech.regency.db.po.OrderStatus.PAYED;
import static com.sustech.regency.util.VerificationUtil.getUserId;
import static com.sustech.regency.web.util.AssertUtil.asserts;
import static com.sustech.regency.util.QRCodeUtil.encode;

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
     * Todo:实现订单超时未支付，参考:<a href="https://juejin.cn/post/7023543229337305124">1</a>,<a href="https://cloud.tencent.com/developer/article/1592068">2</a><p>
     * Todo:实现当前时间超过endTime自动转为NOT_COMMENTED状态
     */
    @Resource
    private AlipayUtil alipayUtil;
    @Resource
    private RoomTypeDao roomTypeDao;
    @Override
    public synchronized String reserveRoom(Integer roomId, Date startDate, Date endDate, List<Cohabitant> cohabitants) {
        asserts(endDate.after(startDate),"退房日期要在入住日期之后");
        asserts(startDate.after(new Date()),"入住日期已过");
        Room room = roomDao.selectById(roomId);
        asserts(room!=null, "房间不存在");
        asserts(orderDao.selectList(
                new LambdaQueryWrapper<Order>()
                   .select(Order::getId) //避免回表
                   .eq(Order::getRoomId,roomId)
                   .le(Order::getDateStart,endDate)
                   .ge(Order::getDateEnd,startDate)
                   .and(queryWrapper->
                        queryWrapper.eq(Order::getStatus, PAYED)
                                    .or() //这两个状态下该房间是被占有的
                                    .eq(Order::getStatus,OrderStatus.NOT_PAYED)))
                .isEmpty(),
                "该房间的该时间段已经被预订");

        int days = (int)DateUtil.betweenDay(startDate, endDate, true); //计算天数
        Order order =new Order()
                        .setRoomId(roomId)
                        .setDateStart(startDate)
                        .setDateEnd(endDate)
                        .setPayerId(getUserId())
                        .setFee(room.getPrice() * days * room.getDiscount());
        orderDao.insert(order);
        //提前设置交易信息
        AlipayTradePrecreateModel alipayInfo = new AlipayTradePrecreateModel();
        alipayInfo.setOutTradeNo(order.getId()+"");
        alipayInfo.setSubject(roomTypeDao.selectById(room.getTypeId()).getName()+days+"天"); //产品名称
        alipayInfo.setTotalAmount(room.getPrice()*days*room.getDiscount()+""); //金额
        alipayInfo.setTimeoutExpress(alipayUtil.getPayTimeout()); //支付超时时间
        String qrCodeUrl = alipayUtil.qrCodePay(alipayInfo);
        return encode(qrCodeUrl,"alipay-logo.png",true);
    }

    @Override
    public void roomPayed(Long orderId) {
        orderDao.updateById(new Order()
                               .setId(orderId)
                               .setStatus(PAYED)
                               .setPayTime(new Date()));
    }

    @Override
    public void like(Integer hotelId) {
        LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collection::getHotelId,hotelId).eq(Collection::getUserId,getUserId());
        Collection one = collectionDao.selectOne(wrapper);
        asserts (one == null ,"You have liked this hotel before");
        Collection collection = Collection.builder().userId(getUserId()).hotelId(hotelId).build();
        collectionDao.insert(collection);
    }

    @Override
    public void dislike(Integer hotelId) {
        LambdaQueryWrapper<Collection> collectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        collectionLambdaQueryWrapper.eq(Collection::getHotelId,hotelId);
        Collection collection = collectionDao.selectOne(collectionLambdaQueryWrapper);
        asserts(collection!=null,"Hotel have not been liked");
        collectionDao.delete(collectionLambdaQueryWrapper);
    }

    @Override
    public List<HotelInfo> getHotelInfoFromLikes() {
        LambdaQueryWrapper<Collection> collectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        collectionLambdaQueryWrapper.eq(Collection::getUserId,getUserId());
        List<Collection> collections = collectionDao.selectList(collectionLambdaQueryWrapper);
        List<HotelInfo> likes = new ArrayList<>();
        for (Collection c:
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
        orderLambdaQueryWrapper.eq(Order::getPayerId,getUserId());
        return orderDao.selectList(orderLambdaQueryWrapper);
    }
}