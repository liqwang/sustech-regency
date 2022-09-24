package com.sustech.regency.service;

public interface UserService {
	String register(String verificationCode, String email, String name, String password, Integer roleId);

	String login(String name, String password);

	void sendVerificationCode(String email);
}
