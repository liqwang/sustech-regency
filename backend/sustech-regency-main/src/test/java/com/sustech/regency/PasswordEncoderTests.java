package com.sustech.regency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTests {
//	@Resource
	private PasswordEncoder passwordEncoder;

	@Test
	void test(){
		String rawPassword = "wlqyanyalun008++";
		Assertions.assertTrue(passwordEncoder.matches(rawPassword,"$2a$10$aJNoSXD4MenFbtqgZJy5tuMkf7y/BdkCCpCgicvwNIuL7LoMfynZe"));
	}
}
