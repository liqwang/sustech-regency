package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.HotelDao;
import com.sustech.regency.db.dao.RoomDao;
import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.db.po.Room;
import com.sustech.regency.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomDao roomDao;

    @Resource
    private HotelDao hotelDao;

    @Override
    public Boolean addNewRoom(Room room) {
        roomDao.insert(room);
        return true;
    }

    @Override
    public Boolean deleteRoom(Integer merchantId, Integer roomId) {
        LambdaQueryWrapper<Room> roomLambdaQueryWrapper = new LambdaQueryWrapper<Room>()
                .eq(Room::getId, roomId);
        Room room = roomDao.selectOne(roomLambdaQueryWrapper);
        LambdaQueryWrapper<Hotel> hotelLambdaQueryWrapper = new LambdaQueryWrapper<Hotel>()
                .eq(Hotel::getId, room.getHotelId());
        Hotel hotel = hotelDao.selectOne(hotelLambdaQueryWrapper);

        //只有自己旗下的酒店才能删除
        if (Objects.equals(hotel.getMerchantId(), merchantId)) {
            roomDao.delete(roomLambdaQueryWrapper);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateOneRoom(Integer merchantId, Integer roomId, Integer roomNum, Float price, Integer floor, Integer typeId, Integer hotelId, Boolean isAvailable, Float discount) {
        LambdaQueryWrapper<Room> roomLambdaQueryWrapper = new LambdaQueryWrapper<Room>()
                .eq(Room::getId, roomId);
        Room roomQ = roomDao.selectOne(roomLambdaQueryWrapper);
        LambdaQueryWrapper<Hotel> hotelLambdaQueryWrapper = new LambdaQueryWrapper<Hotel>()
                .eq(Hotel::getId, roomQ.getHotelId());
        Hotel hotel = hotelDao.selectOne(hotelLambdaQueryWrapper);

        //只有自己旗下的酒店才能删除
        if (Objects.equals(hotel.getMerchantId(), merchantId)) {
            Room room = new Room();
            if (roomId != null) room.setId(roomId);
            if (roomNum != null) room.setRoomNum(roomNum);
            if (price != null) room.setPrice(price);
            if (floor != null) room.setFloor(floor);
            if (typeId != null) room.setTypeId(typeId);
            if (hotelId != null) room.setHotelId(hotelId);
            if (isAvailable != null) room.setIsAvailable(isAvailable);
            if (discount != null) room.setDiscount(discount);
            roomDao.updateById(room);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateRooms(Integer merchantId, Integer hotelId, Integer typeId, Float price, Float discount) {
        Hotel hotel = hotelDao.selectById(hotelId);
        if (Objects.equals(merchantId, hotel.getMerchantId())) {
            LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
            if (hotelId != null) wrapper.eq(Room::getHotelId, hotelId);
            if (typeId != null) wrapper.eq(Room::getTypeId, typeId);
            List<Room> roomList = roomDao.selectList(wrapper);
            for (Room room : roomList) {
                if (price != null) room.setPrice(price);
                if (discount != null) room.setDiscount(discount);
                roomDao.updateById(room);
            }
            return true;
        }
        return false;
    }

    @Override
    public Room getOneRoom(Integer roomId) {
        return null;
    }
}
