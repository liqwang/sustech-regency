package com.sustech.regency.web.config;

import com.sustech.regency.web.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*当有AuthenticationManager的Bean时，使用JUnit会StackOverFlowError
	  参考https://www.cnblogs.com/seliote/p/15096901.html
	     https://stackoverflow.com/questions/67546793/why-is-spring-boot-2-4-5-with-junit5-and-mocked-beans-is-throwing-stackoverflowe*/
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		 return http.csrf().disable() //关闭csrf
				    .cors().and() //开启SpringSecurity跨域
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //关闭Session(不通过Session获取SecurityContext)
					.authorizeRequests(
						authorize->authorize
								  .antMatchers("/user/login").anonymous() //只允许登录接口匿名访问，认证通过后无法访问
								  .antMatchers("/doc.html","/webjars/**","/img.icons/**","/swagger-resources","/v2/api-docs","/favicon.ico").permitAll() //放行Knife4j相关URL
								  .anyRequest().authenticated()
					)
				    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and() //认证失败
//				                        .accessDeniedHandler(accessDeniedHandler).and() //鉴权失败
				    .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class) //在UsernamePasswordAuthenticationFilter**之前**添加JWT过滤器(为什么?)
//					.userDetailsService(userDetailsService) //注入userDetailsService
					.build();
	}

	/**
	 * 开启SpringBoot跨域
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**") //允许所有API
				.allowedOriginPatterns("*") //允许所有域名
				.allowCredentials(true) //允许Cookie
				.allowedMethods("*") //允许任何方法
				.allowedHeaders("*") //允许任何header
				.maxAge(3600); //跨域允许时间1h
	}
}
