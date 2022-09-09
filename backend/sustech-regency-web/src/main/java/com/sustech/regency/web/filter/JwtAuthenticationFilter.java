package com.sustech.regency.web.filter;

import com.sustech.regency.db.util.Redis;
import com.sustech.regency.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private Redis redis;
//	@Autowired
//	private UserDao userDao;

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request,
	                                @NotNull HttpServletResponse response,
	                                @NotNull FilterChain filterChain) throws ServletException, IOException {
		//每次请求都是一个独立的SecurityContext
		String token = request.getHeader("token");
		if (StringUtils.hasText(token)){
			Claims claims = JwtUtil.parseJwt(token);
			String sid = claims.getSubject();
//			User user = redis.getObject("login:" + sid);
//			if (user == null) { //Redis的User过期了，查询数据库
//				user = userDao.selectById(sid);
//			}
//			if (user != null) { //如果Redis和数据库中都没有User，则SecurityContext中没有Authentication对象
//				redis.setObject("login:" + sid, user, 3600); //刷新ttl为1h
//				Authentication authentication = new UsernamePasswordAuthenticationToken(user.getSid(), user.getPassword(), null);
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			}
		}
		filterChain.doFilter(request, response); //没有user也直接放行，之后会被Interceptor拦截，因为SecurityContext中没有Authentication对象
	}
}
