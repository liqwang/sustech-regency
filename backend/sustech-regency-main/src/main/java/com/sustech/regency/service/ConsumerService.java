package com.sustech.regency.service;

import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.model.vo.HotelInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ConsumerService {
    /**
     * @return 文件上传成功后的获取URL
     */
    String uploadCommentMedia(MultipartFile media, Integer orderId);

    void deleteCommentMedia(String mediaId, Integer orderId);

    void RoomReservation(Integer roomId, Date startTime, Date endTime, Float price, Integer payerId, String payerName, String payerIdNumber, List<String> cohabitantIdNumbers, List<String> cohabitantNames);

    void like(Integer hotelId);

    void dislike(Integer hotelId);

    List<HotelInfo> getHotelInfoFromLikes();

}
