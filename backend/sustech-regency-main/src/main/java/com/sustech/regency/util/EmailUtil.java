package com.sustech.regency.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EmailUtil {

	@Resource
	private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String from;

	public void sendMail(String to,String content,String subject){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		mailSender.send(message);
	}
}
