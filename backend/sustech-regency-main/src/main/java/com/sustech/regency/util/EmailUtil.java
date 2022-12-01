package com.sustech.regency.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EmailUtil {

	@Resource
	private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String from;

	public void sendMail(String to,String content,String subject){
		sendMail(List.of(to),content,subject);
	}

	/**
	 * 缺点：每个收件人都可以看到所有收件人的邮箱，容易暴露隐私
	 */
	public void sendMail(List<String> to, String content, String subject){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to.toArray(new String[0]));
		message.setSubject(subject);
		message.setText(content);
		mailSender.send(message);
	}
}
