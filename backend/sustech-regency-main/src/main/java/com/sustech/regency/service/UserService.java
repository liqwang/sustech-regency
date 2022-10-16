package com.sustech.regency.service;

public interface UserService {
	String register(String verificationCode, String email, String name, String password, Integer roleId);

	String login(String usernameOrEmail, String password);

	void findPassword(String verificationCode,String email,String newPassword);

	void sendVerificationCode(String email);
}
