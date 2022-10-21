package com.sustech.regency.service.impl;

import com.sustech.regency.db.dao.UserDao;
import com.sustech.regency.db.po.User;
import com.sustech.regency.service.HideService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HideServiceImpl implements HideService {
    @Resource
    private UserDao userDao;

    @Override
    public void addUser(User user) {
        userDao.insert(user);
    }
}
