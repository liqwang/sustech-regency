package com.sustech.regency.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EmailUtilTests {

	@Resource
	private EmailUtil emailUtil;
	@Test
	void sendEmail(){
		emailUtil.sendMail("12011619@mail.sustech.edu.cn","Test","Subject");
	}
}
