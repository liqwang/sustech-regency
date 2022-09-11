package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.UserDao;
import com.sustech.regency.db.dao.UserWithRoleDao;
import com.sustech.regency.db.po.User;
import com.sustech.regency.db.po.UserWithRole;
import com.sustech.regency.db.util.Redis;
import com.sustech.regency.service.UserService;
import com.sustech.regency.web.handler.ApiException;
import com.sustech.regency.web.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
	private UserDao userDao;
	@Autowired
	private UserWithRoleDao userWithRoleDao;
	@Autowired
	private Redis redis;

	@Override
	public String register(String name, String password, Integer roleId) {
		//判断该name是否已经存在
		User user = userDao.selectOne(new LambdaQueryWrapper<User>()
										 .select(User::getId)
										 .eq(User::getName, name));
		if(user==null){
			user=User.builder()
				.name(name)
				.password(passwordEncoder.encode(password))
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
		return JwtUtil.createJwt(String.valueOf(user.getId())); //使用sid生成JWT返回
	}

	@Override
	public String login(String name, String password) {
		return null;
	}
}
