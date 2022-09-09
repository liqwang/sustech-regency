package com.sustech.regency.service;

public interface UserService {
	String register(String name, String password);

	String login(String name, String password);
}
