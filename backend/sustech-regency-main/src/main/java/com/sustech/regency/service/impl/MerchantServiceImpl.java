package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.regency.db.dao.HotelDao;
import com.sustech.regency.db.dao.UserDao;
import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.db.po.Order;
import com.sustech.regency.db.po.Room;
import com.sustech.regency.db.po.User;
import com.sustech.regency.service.MerchantService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private HotelDao hotelDao;


    @Override
    public List<Hotel> getAllHotels(Integer merchantId, Integer cityId) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",merchantId);
        if(cityId!=null) map.put("city_id",cityId);
        return hotelDao.selectByMap(map);
    }

    @Override
    public Boolean addNewHotel(Hotel hotel) {
        hotelDao.insert(hotel);
        return true;
    }

    @Override
    public Boolean deleteHotel(Integer merchantId,Integer hotelId) {
//        new LambdaQueryWrapper<Hotel>()
//                .eq(Hotel::getId,)
        QueryWrapper<Hotel> wrapper= new QueryWrapper<>();
        wrapper.eq("merchant_id",merchantId);
        wrapper.eq("id", hotelId);
        Hotel query=hotelDao.selectOne(wrapper);
        //只有自己旗下的酒店才能删除
        if(query==null){
            return false;
        }
        hotelDao.deleteById(hotelId);
        return true;
    }

    @Override
    public Boolean updateHotel(Integer hotelId,Float latitude, Float longitude,Integer cityId, Integer merchantId, String name, String tel){
        QueryWrapper<Hotel> wrapper= new QueryWrapper<>();
        wrapper.eq("merchant_id",merchantId);
        wrapper.eq("id", hotelId);
        Hotel query=hotelDao.selectOne(wrapper);
        //只有自己旗下的酒店才能删除
        if(query==null){
            return false;
        }else{
            Hotel hotel = new Hotel();
            if(hotelId!=null) hotel.setId(hotelId);
            if(latitude!=null) hotel.setLatitude(latitude);
            if (longitude!=null) hotel.setLongitude(longitude);
            if(cityId!=null) hotel.setCityId(cityId);
            if(merchantId!=null) hotel.setMerchantId(merchantId); //比如酒店转让
            if(name!=null) hotel.setName(name);
            if(tel!=null) hotel.setTel(tel);
            int i=hotelDao.updateById(hotel);
        }
        return true;
    }

    @Override
    public Hotel getOneHotel(Integer hotelId,Float latitude, Float longitude,Integer cityId, Integer merchantId, String name, String tel) {
        QueryWrapper<Hotel> wrapper=new QueryWrapper<>();
        if(hotelId!=null) wrapper.eq("id",hotelId);
        if(latitude!=null) wrapper.eq("latitude",latitude);
        if(longitude!=null) wrapper.eq("longitude",longitude);
        if(cityId!=null) wrapper.eq("city_id",cityId);
        if(name!=null) wrapper.eq("name",name);
        if(tel!=null) wrapper.eq("tel",tel);
        Hotel hotel = hotelDao.selectOne(wrapper);
        if(Objects.equals(hotel.getMerchantId(), merchantId)){
            return hotel;
        }
        return null;
    }

    @Override
    public List<Order> getOrders(Integer merchantId,Integer hotelId,Integer roomId,Integer cityId) {
        //晚些实现，到时候看看前端需要那些参数可以查订单
        return null;
    }
}
