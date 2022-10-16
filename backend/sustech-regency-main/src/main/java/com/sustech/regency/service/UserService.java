package com.sustech.regency.service;

import java.util.Map;

public interface UserService {
	/**
	 * @return token以及用户的详细信息
	 */
	Map<String,Object> register(String verificationCode, String email, String username, String password, Integer roleId);

	/**
	 * @return token以及用户的详细信息
	 */
	Map<String,Object> login(String usernameOrEmail, String password);

	void findPassword(String verificationCode,String email,String newPassword);

	void sendVerificationCode(String email);
}
