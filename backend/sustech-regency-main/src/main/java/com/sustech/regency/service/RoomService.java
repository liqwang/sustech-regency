package com.sustech.regency.service;

import com.sustech.regency.db.po.Room;

public interface RoomService {
    //商家增加新的房间
    Boolean addNewRoom(Room room);

    //商家删除某个房间
    Boolean deleteRoom(Integer merchantId, Integer roomId);

    //商家修改某个房间的配置如价格
    Boolean updateOneRoom(Integer merchantId, Integer roomId, Integer roomNum, Float price, Integer floor, Integer typeId, Integer hotelId, Boolean isAvailable, Float discount);

    //商家修改一系列房间的配置如价格(如同一个酒店所有房间同时打折，或某个具体的房型打折)
    Boolean updateRooms(Integer merchantId, Integer hotelId, Integer typeId, Float price, Float discount);

    //查询某个房间的信息
    Room getOneRoom(Integer roomId);


}
