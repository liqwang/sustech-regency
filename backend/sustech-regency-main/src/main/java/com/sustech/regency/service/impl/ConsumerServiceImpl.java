package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.*;
import com.sustech.regency.db.po.*;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.service.HideService;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private HotelDao hotelDao;

    @Resource
    private RoomDao roomDao;

    @Resource
    private HotelExhibitionDao hotelExhibitionDao;

    @Resource
    private HideService hideService;

    @Resource
    private CheckInDao checkInDao;

    @Resource
    private UserDao userDao;

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

    //后面加支付，看是money类还是调用支付宝api
    @Override
    public void RoomReservation(Integer roomId, Date startTime, Date endTime, Float price, Integer payerId, String payerName, String payerIdNumber, List<String> cohabitantIdNumbers, List<String> cohabitantNames) {
        //判断房间存不存在
        Room room = roomDao.selectById(roomId);
        asserts(room != null, "Room dose not exist");
        //先看根据房间Id查时间，看时间合不合法，如果合法再继续
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getRoomId, roomId);
        List<Order> orders = orderDao.selectList(orderLambdaQueryWrapper);
        for (Order o : orders) {
            asserts(endTime.before(o.getDateStart()) && startTime.after(o.getDateEnd()), "Date Conflict!");
        }
        //能活到这里说明日期合法了,需要加到订单里
        Order order = Order.builder().roomId(roomId)
                .dateStart(startTime)
                .dateEnd(endTime)
                .payerId(payerId)
                .fee(price)
                .payerId(payerId)
                .status(1)
                .build();
        //把付款人加进check_in
        CheckIn checkIn = CheckIn.builder().userId(payerId)
                .orderId(order.getId()).build();
        checkInDao.insert(checkIn);
        //存付款人身份证号
        ///////
        //看同住人是否在表中了，不在的话先加进user表，再加进check_in里面
        for (int i = 0; i < cohabitantIdNumbers.size(); i++) {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getIdNumber, cohabitantIdNumbers.get(i));
            User user = userDao.selectOne(userLambdaQueryWrapper);
            if (user == null) {
                User newUser = User.builder().idNumber(cohabitantIdNumbers.get(i))
                        .name(cohabitantNames.get(i)).build();
                hideService.addUser(newUser);
                checkIn = CheckIn.builder().userId(newUser.getId())
                        .orderId(order.getId()).build();
                checkInDao.insert(checkIn);
            } else {
                checkIn = CheckIn.builder().userId(user.getId()).orderId(order.getId()).build();
                checkInDao.insert(checkIn);
            }
        }
    }

    @Override
    public void like(Integer hotelId) {
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
            LambdaQueryWrapper<Hotel> hotelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            hotelLambdaQueryWrapper.eq(Hotel::getId,hotel_id);
            HotelInfo hotel = publicService.getOneHotelByHotelId(hotel_id);
            likes.add(hotel);
        }
        return likes;
    }
}