package com.sustech.regency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTests {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void test(){
		String rawPassword = "wlqyanyalun008++";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		Assertions.assertTrue(passwordEncoder.matches(rawPassword,encodedPassword));
	}
}
