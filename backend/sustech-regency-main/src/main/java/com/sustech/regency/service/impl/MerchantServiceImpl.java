package com.sustech.regency.service.impl;

import com.sustech.regency.db.dao.HotelDao;
import com.sustech.regency.db.dao.UserDao;
import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.db.po.User;
import com.sustech.regency.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private HotelDao hotelDao;


    @Override
    public List<Hotel> getAllHotels(Integer merchantId) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",merchantId);
        return hotelDao.selectByMap(map);
    }
}
