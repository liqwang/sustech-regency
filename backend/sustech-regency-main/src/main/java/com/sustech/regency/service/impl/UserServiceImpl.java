package com.sustech.regency.service.impl;

import com.sustech.regency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String register(String name, String password) {
		return null;
	}

	@Override
	public String login(String name, String password) {
		return null;
	}
}
