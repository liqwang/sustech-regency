package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.LoginLogDao;
import com.sustech.regency.db.dao.UserDao;
import com.sustech.regency.db.dao.UserWithRoleDao;
import com.sustech.regency.db.po.LoginLog;
import com.sustech.regency.db.po.User;
import com.sustech.regency.db.po.UserWithRole;
import com.sustech.regency.db.util.Redis;
import com.sustech.regency.service.UserService;
import com.sustech.regency.util.VerificationUtil;
import com.sustech.regency.web.handler.ApiException;
import com.sustech.regency.web.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private Redis redis;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserWithRoleDao userWithRoleDao;
	@Autowired
	private LoginLogDao loginLogDao;

	@Override
	public String register(String verificationCode, String email, String name, String password, Integer roleId) {
		String trueCode = redis.getObject("verification:" + email);
		if(trueCode==null){
			throw ApiException.badRequest("验证码已过期，请重新发送");
		}else if(!trueCode.equals(verificationCode)){
			throw ApiException.badRequest("验证码错误");
		}
		//判断该name是否已经存在
		User user = userDao.selectOne(new LambdaQueryWrapper<User>()
										 .eq(User::getName, name));
		if(user==null){
			user=User.builder()
				.name(name)
				.password(passwordEncoder.encode(password))
				.email(email)
				.build();
			userDao.insert(user);
		}else{//用户已存在
			//查询是否已为该role
			UserWithRole userWithRole = userWithRoleDao.selectOne(new LambdaQueryWrapper<UserWithRole>()
																	 .eq(UserWithRole::getUserId, user.getId())
																	 .eq(UserWithRole::getRoleId, roleId));
			if(userWithRole!=null){
				throw new ApiException(400,"无法重复注册"+(roleId==1?"消费者":"商家"));
			}
		}
		userWithRoleDao.insert(new UserWithRole(user.getId(),roleId,new Date()));
		//直接认证通过，就不经过AuthenticationManager#authenticate了
		Authentication authentication=new UsernamePasswordAuthenticationToken(user.getId(),user.getPassword(),null);
		SecurityContextHolder.getContext().setAuthentication(authentication); //存入SecurityContext
		redis.setObject("login:"+user.getId(),user,60*60*2); //把完整用户信息存入Redis, sid作为key, ttl为2h
		return JwtUtil.createJwt(String.valueOf(user.getId())); //使用id生成JWT返回
	}

	@Override
	public void findPassword(String verificationCode, String email, String newPassword) {
		User user = userDao.selectOne(new LambdaQueryWrapper<User>()
										 .eq(User::getEmail, email));
		if(user==null){throw ApiException.badRequest("邮箱未被绑定");}
			String trueCode = redis.getObject("verification:" + email);
		if(trueCode==null){
			throw ApiException.badRequest("验证码已过期，请重新发送");
		}else if(!trueCode.equals(verificationCode)){
			throw ApiException.badRequest("验证码错误");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		userDao.update(user, new LambdaQueryWrapper<User>()
						        .eq(User::getId,user.getId()));
	}

	@Override
	public String login(String name, String password) {
		//判断该name是否存在
		User user = userDao.selectOne(new LambdaQueryWrapper<User>()
										 .eq(User::getName, name));
		if(user==null){
			throw new ApiException(400,"User doesn't exists, please register first");
		}else if(!passwordEncoder.matches(password,user.getPassword())){
			throw new ApiException(400,"Password wrong");
		}else{
			//生成LoginLog
			loginLogDao.insert(new LoginLog(user.getId(),new Date()));
			//直接认证通过，就不经过AuthenticationManager#authenticate了
			Authentication authentication=new UsernamePasswordAuthenticationToken(user.getId(),user.getPassword(),null);
			SecurityContextHolder.getContext().setAuthentication(authentication); //存入SecurityContext
			redis.setObject("login:"+user.getId(),user,60*60*2); //把完整用户信息存入Redis, sid作为key, ttl为2h
			return JwtUtil.createJwt(String.valueOf(user.getId())); //使用id生成JWT返回
		}
	}

	@Autowired
	private JavaMailSender javaMailSender;
	@Override
	public void sendVerificationCode(String email) {
		//1.发送验证码
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("836200779@qq.com");
		message.setTo(email);
		message.setSubject("SUSTech-Regency邮箱验证");
		String randomCode = VerificationUtil.generateVerificationCode();
		message.setText("验证码:"+randomCode+", 有效期2分钟");
		javaMailSender.send(message);
		//2.存入Redis
		redis.setObject("verification:"+email,randomCode,120);
	}
}
