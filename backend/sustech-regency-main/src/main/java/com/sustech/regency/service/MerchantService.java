package com.sustech.regency.service;

import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.db.po.Order;
import com.sustech.regency.db.po.Room;
import com.sustech.regency.db.po.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.jdbc.Null;

import java.lang.reflect.Type;
import java.util.List;

public interface MerchantService {
    //得到某个商家旗下的所有酒店
    List<Hotel> getAllHotels(Integer merchantId, Integer cityId);
    //商家登记新酒店
    Boolean addNewHotel(Hotel hotel);
    //商家删除酒店
    Boolean deleteHotel(Integer merchantId,Integer hotelId);
    //商家更新酒店信息，如名称
    Boolean updateHotel(Integer hotelId,Float latitude, Float longitude,Integer cityId, Integer merchantId, String name, String tel);
    //得到旗下某个酒店的所有房间信息
    Hotel getOneHotel(Integer hotelId,Float latitude, Float longitude,Integer cityId, Integer merchantId, String name, String tel);
    //商家修改某个房间的配置如价格
    void updateRoom(Integer RoomId);
    //商家增加新的房间
    void addNewRoom(Room room);
    //商家删除某个房间
    void deleteRoom(Integer roomId);
    //商家查询某个房间的信息
    Room getRoomById(Integer roomId);
    //商家查询某个房型的入住情况
    List<Room> getRoomByRoomType(Integer typeId);
    //查询某个商家的所有订单
    List<Order> getOrders(Integer merchantId);
    //这个多参数是不是传入等于null即可？




}
