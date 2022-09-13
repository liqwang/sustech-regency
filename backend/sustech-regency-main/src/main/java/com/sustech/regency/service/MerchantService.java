package com.sustech.regency.service;

import com.sustech.regency.db.po.Hotel;
import com.sustech.regency.db.po.User;

import java.util.List;

public interface MerchantService {
    List<Hotel> getAllHotels(Integer merchantId);
}
