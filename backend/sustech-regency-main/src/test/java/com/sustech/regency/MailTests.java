package com.sustech.regency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class MailTests {
	@Resource
	private JavaMailSender javaMailSender;

	@Test
	void test(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("836200779@qq.com");
		message.setTo("12011619@mail.sustech.edu.cn");
		message.setSubject("主题:yfsb");
		message.setText("yfsb");
		javaMailSender.send(message);
	}
}
