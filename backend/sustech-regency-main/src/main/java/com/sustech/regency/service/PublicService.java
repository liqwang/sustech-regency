package com.sustech.regency.service;

import com.sustech.regency.db.po.Room;
import com.sustech.regency.model.vo.HotelInfo;

import java.util.List;

public interface PublicService {
    //通过省市区的到酒店信息
    List<HotelInfo> getHotelsByLocation(String province, String city, String region, String hotelName);

    List<Room> getRoomsByHotel(Integer hotelId);


    List<String> getPictureUrls(Integer hotelId);

    List<String> getVideoUrls(Integer hotelId);

}
