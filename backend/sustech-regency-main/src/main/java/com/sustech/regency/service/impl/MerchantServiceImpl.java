package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sustech.regency.db.dao.*;
import com.sustech.regency.db.po.*;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Resource
    private HotelDao hotelDao;

    @Override
    public List<HotelInfo> getAllHotelInfos(Integer merchantId) {
        return hotelDao.selectJoinList(
                HotelInfo.class,
                new MPJLambdaWrapper<HotelInfo>()
                        .select(Hotel::getId, Hotel::getLatitude, Hotel::getLongitude, Hotel::getName, Hotel::getTel, Hotel::getAddress)
                        .selectAs(Province::getName, HotelInfo::getProvinceName)
                        .selectAs(City::getName, HotelInfo::getCityName)
                        .selectAs(Region::getName, HotelInfo::getRegionName)
                        .innerJoin(Region.class, Region::getId, Hotel::getRegionId)
                        .innerJoin(City.class, City::getId, Region::getCityId)
                        .innerJoin(Province.class, Province::getId, City::getProvinceId)
                        .eq(Hotel::getMerchantId, merchantId));
    }

    @Override
    public Boolean addNewHotel(Hotel hotel) {
        hotelDao.insert(hotel);
        return true;
    }

    @Override
    public Boolean deleteHotel(Integer merchantId, Integer hotelId) {
//        new LambdaQueryWrapper<Hotel>()
//                .eq(Hotel::getId,)
        QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId);
        wrapper.eq("id", hotelId);
        Hotel query = hotelDao.selectOne(wrapper);
        //只有自己旗下的酒店才能删除
        if (query == null) {
            return false;
        }
        hotelDao.deleteById(hotelId);
        return true;
    }

    @Override
    public Boolean updateHotel(Integer hotelId, Float latitude, Float longitude, Integer regionId, Integer merchantId, String name, String tel, String address) {
        QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId);
        wrapper.eq("id", hotelId);
        Hotel query = hotelDao.selectOne(wrapper);
        //只有自己旗下的酒店才能删除
        if (query == null) {
            return false;
        } else {
            Hotel hotel = new Hotel();
            if (hotelId != null) hotel.setId(hotelId);
            if (latitude != null) hotel.setLatitude(latitude);
            if (longitude != null) hotel.setLongitude(longitude);
            if (regionId != null) hotel.setRegionId(regionId);
            if (merchantId != null) hotel.setMerchantId(merchantId); //比如酒店转让
            if (name != null) hotel.setName(name);
            if (tel != null) hotel.setTel(tel);
            hotelDao.updateById(hotel);
        }
        return true;
    }

    @Override
    public HotelInfo getOneHotel(Integer hotelId, Float latitude, Float longitude, Integer merchantId, String name, String tel) {
//        QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
        MPJLambdaWrapper<HotelInfo> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Hotel::getId, Hotel::getLatitude, Hotel::getLongitude, Hotel::getName, Hotel::getTel, Hotel::getAddress)
                .selectAs(Province::getName, HotelInfo::getProvinceName)
                .selectAs(City::getName, HotelInfo::getCityName)
                .selectAs(Region::getName, HotelInfo::getRegionName);
        if (hotelId != null) wrapper.eq(Hotel::getId, hotelId);
        if (latitude != null) wrapper.eq(Hotel::getLatitude, latitude);
        if (longitude != null) wrapper.eq(Hotel::getLongitude, longitude);
        if (name != null) wrapper.eq(Hotel::getName, name);
        if (tel != null) wrapper.eq(Hotel::getTel, tel);

        wrapper.innerJoin(Region.class, Region::getId, Hotel::getRegionId)
                .innerJoin(City.class, City::getId, Region::getCityId)
                .innerJoin(Province.class, Province::getId, City::getProvinceId)
                .eq(Hotel::getMerchantId, merchantId);
        return hotelDao.selectJoinOne(HotelInfo.class, wrapper);
    }

    @Override
    public List<Order> getOrders(Integer merchantId, Integer hotelId, Integer roomId, Integer cityId) {
        //晚些实现，到时候看看前端需要那些参数可以查订单
        return null;
    }
}
