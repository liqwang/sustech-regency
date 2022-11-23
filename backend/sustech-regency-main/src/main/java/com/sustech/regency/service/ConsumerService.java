package com.sustech.regency.service;

import com.sustech.regency.db.po.Order;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.model.vo.PayInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ConsumerService {
    /**
     * @return 文件上传成功后的获取URL
     */
    String uploadCommentMedia(MultipartFile media, Integer orderId);

    void deleteCommentMedia(String mediaId, Integer orderId);

    void cancelOrder(Long orderId);

    /**
     * @return 支付二维码图片的Base64编码
     */
    PayInfo reserveRoom(Integer roomId, Date startTime, Date endTime);

    void roomPayed(Long orderId,Date payTime);

    void like(Integer hotelId);

    void dislike(Integer hotelId);

    List<HotelInfo> getHotelInfoFromLikes();

    List<Order> getOrders();
}
