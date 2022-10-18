package com.sustech.regency.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sustech.regency.db.dao.*;
import com.sustech.regency.db.po.*;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.util.FileUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublicServiceImpl implements PublicService {
    @Resource
    private HotelDao hotelDao;

    @Resource
    private RoomDao roomDao;

    @Resource
    private FileDao fileDao;

    @Resource
    private HotelExhibitionDao hotelExhibitionDao;

    @Resource
    private OrderDao orderDao;

    @Override
    public List<HotelInfo> getHotelsByLocation(String province, String city, String region, String hotelName) {
        MPJLambdaWrapper<HotelInfo> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Hotel::getId, Hotel::getLatitude, Hotel::getLongitude, Hotel::getName, Hotel::getTel, Hotel::getAddress, Hotel::getStars)
                .selectAs(Province::getName, HotelInfo::getProvinceName)
                .selectAs(City::getName, HotelInfo::getCityName)
                .selectAs(Region::getName, HotelInfo::getRegionName)
                .selectAs(File::getId, HotelInfo::getCoverUrl);
        if (Strings.isNotEmpty(province)) wrapper.eq(Province::getName, province);
        if (Strings.isNotEmpty(city)) wrapper.eq(City::getName, city);
        if (Strings.isNotEmpty(region)) wrapper.eq(Region::getName, region);
        if (Strings.isNotEmpty(hotelName)) wrapper.eq(Hotel::getName, hotelName);
        wrapper.innerJoin(Region.class, Region::getId, Hotel::getRegionId)
                .innerJoin(City.class, City::getId, Region::getCityId)
                .innerJoin(Province.class, Province::getId, City::getProvinceId);
//                .innerJoin(File.class, File::getSuffix, Hotel::getCoverId);

        List<HotelInfo> hotelInfos = hotelDao.selectJoinList(HotelInfo.class, wrapper);
        for (HotelInfo a :
                hotelInfos) {
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, a.getCoverUrl());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if(file!=null){
                if(file.getDeleteTime()==null) a.setCoverUrl(FileUtil.getUrl(file));
            }
            a.setPictureUrls(getPictureUrls(a.getId()));
            a.setVideoUrls(getVideoUrls(a.getId()));

        }
        return hotelInfos;
    }

    @Override
    public List<Room> getRoomsByHotel(Integer hotelId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getHotelId, hotelId);
        return roomDao.selectList(wrapper);
    }

    @Override
    public List<String> getPictureUrls(Integer hotelId) {
        LambdaQueryWrapper<HotelExhibition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HotelExhibition::getHotelId, hotelId);
        List<HotelExhibition> list = hotelExhibitionDao.selectList(wrapper);
        List<String> pictureList = new ArrayList<>();

        for (HotelExhibition he :
                list) {
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, he.getFileId());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if (file!=null && !file.getSuffix().equals("mp4")) {
                if(file.getDeleteTime()==null) pictureList.add(FileUtil.getUrl(file));

            }
        }
        return pictureList;
    }


    @Override
    public List<String> getVideoUrls(Integer hotelId) {
        LambdaQueryWrapper<HotelExhibition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HotelExhibition::getHotelId, hotelId);
        List<HotelExhibition> list = hotelExhibitionDao.selectList(wrapper);
        List<String> videoList = new ArrayList<>();

        for (HotelExhibition he :
                list) {
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, he.getFileId());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if (file!=null &&file.getSuffix().equals("mp4")) {
                if(file.getDeleteTime()==null) videoList.add(FileUtil.getUrl(file));
            }

        }
        return videoList;
    }

    @Override
    public Float getMinPriceOfHotel(Integer hotelId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getHotelId,hotelId);
        List<Room> roomList = roomDao.selectList(wrapper);
        float min_price= Float.MAX_VALUE;
        for (Room r:
             roomList) {
            float curr_price=r.getPrice()*r.getDiscount();
            if( curr_price < min_price) min_price=curr_price;
        }
        return min_price;
    }

    @Override
    public Integer getCommentsNumberByHotel(Integer hotelId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getHotelId,hotelId);
        List<Room> roomList = roomDao.selectList(wrapper);
        int cnt = 0;
        for (Room r:
             roomList) {
            int room_id=r.getId();
            LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderLambdaQueryWrapper.eq(Order::getRoomId,room_id);
            List<Order> orderList = orderDao.selectList(orderLambdaQueryWrapper);
            cnt+=orderList.size();
        }
        return cnt;
    }
}
