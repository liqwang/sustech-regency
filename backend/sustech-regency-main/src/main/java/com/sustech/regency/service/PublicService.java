package com.sustech.regency.service;

import com.sustech.regency.db.po.Room;
import com.sustech.regency.db.po.RoomType;
import com.sustech.regency.model.vo.Comment;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.model.vo.RoomInfo;

import java.util.List;

public interface PublicService {
    //通过省市区的到酒店信息
    List<HotelInfo> getHotelsByLocation(String province, String city, String region, String hotelName);

    List<Room> getRoomsByHotel(Integer hotelId,Integer roomTypeId);

    List<String> getHotelPictureUrls(Integer hotelId);

    List<String> getHotelVideoUrls(Integer roomlId);

    List<String> getRoomTypePictureUrls(Integer hotelId);

    List<String> getRoomTypeVideoUrls(Integer roomId);


    Float getMinPriceOfHotel(Integer hotelId);

    Integer getCommentsNumberByHotel(Integer hotelId);

    RoomInfo getRoomInfoByRoomId(Integer roomId);

    List<RoomType> getRoomTypesByHotelId(Integer hotelId);

    String getMerchantUsernameByHotelId(Integer hotelId);

    List<Comment> getCommentsByHotelId(Integer hotelId);

    HotelInfo getOneHotelByHotelId(Integer hotelId);

    Integer getRoomIdByHotelWithRoomNum(Integer hotelId, Integer roomNum);

    List<RoomType> getRoomTypesByHotel(Integer hotelId);

    Integer getLikesNumByHotelId(Integer hotelId);



}
