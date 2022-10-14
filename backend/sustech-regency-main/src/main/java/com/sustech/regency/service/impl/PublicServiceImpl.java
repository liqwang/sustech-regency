package com.sustech.regency.service.impl;


import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sustech.regency.db.dao.HotelDao;
import com.sustech.regency.db.po.City;
import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.db.po.Province;
import com.sustech.regency.db.po.Region;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.service.PublicService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PublicServiceImpl implements PublicService {
    @Resource
    private HotelDao hotelDao;

    @Override
    public List<HotelInfo> getHotelsByLocation(String province, String city, String region, String hotelName) {
        MPJLambdaWrapper<HotelInfo> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Hotel::getId, Hotel::getLatitude, Hotel::getLongitude, Hotel::getName, Hotel::getTel, Hotel::getAddress)
                .selectAs(Province::getName, HotelInfo::getProvinceName)
                .selectAs(City::getName, HotelInfo::getCityName)
                .selectAs(Region::getName, HotelInfo::getRegionName);
        if (Strings.isNotEmpty(province)) wrapper.eq(Province::getName, province);
        if (Strings.isNotEmpty(city)) wrapper.eq(City::getName, city);
        if (Strings.isNotEmpty(region)) wrapper.eq(Region::getName, region);
        if (Strings.isNotEmpty(hotelName)) wrapper.eq(Hotel::getName, hotelName);
        wrapper.innerJoin(Region.class, Region::getId, Hotel::getRegionId)
                .innerJoin(City.class, City::getId, Region::getCityId)
                .innerJoin(Province.class, Province::getId, City::getProvinceId);
        return hotelDao.selectJoinList(HotelInfo.class, wrapper);
    }
}
