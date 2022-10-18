package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sustech.regency.db.dao.FileDao;
import com.sustech.regency.db.dao.HotelDao;
import com.sustech.regency.db.dao.HotelExhibitionDao;
import com.sustech.regency.db.po.*;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.MerchantService;
import com.sustech.regency.util.FileUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.io.FileUtil.getSuffix;
import static com.sustech.regency.util.FileUtil.VALID_PICTURE_SUFFIXES;
import static com.sustech.regency.util.FileUtil.checkMediaSuffix;
import static com.sustech.regency.util.FileUtil.getUUID;
import static com.sustech.regency.web.util.AssertUtil.asserts;

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
    public Boolean updateHotel(Integer hotelId, Float latitude, Float longitude, Integer merchantId, String name, String tel, String address) {
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
            if (merchantId != null) hotel.setMerchantId(merchantId); //比如酒店转让
            if (name != null) hotel.setName(name);
            if (tel != null) hotel.setTel(tel);
            hotelDao.updateById(hotel);
        }
        return true;
    }

    @SuppressWarnings("DuplicatedCode")
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

    @Resource
    private FileUtil fileUtil;
    @Resource
    private HotelExhibitionDao hotelExhibitionDao;
    @Override
    public String uploadHotelMedia(MultipartFile file, Integer hotelId) {
        checkMediaSuffix(file);
        checkHotelAndOwner(hotelDao.selectById(hotelId));

        String uuid = getUUID();
        String url = fileUtil.uploadFile(file,uuid);
        HotelExhibition hotelExhibition = new HotelExhibition(hotelId, uuid);
        hotelExhibitionDao.insert(hotelExhibition);
        return url;
    }

    private void checkHotelAndOwner(Hotel hotel){
        asserts(hotel!=null,"酒店不存在");
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        asserts(merchantId==hotel.getMerchantId(),"该酒店属于别人");
    }
}
