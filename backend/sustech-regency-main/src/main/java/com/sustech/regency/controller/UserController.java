package com.sustech.regency.controller;

import com.sustech.regency.service.UserService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@ApiOperation("注册")
	@PostMapping("/register")
	public ApiResponse<Map> register(@ApiParam(value="用户名", required=true) @RequestParam String username,
	                                 @ApiParam(value="密码", required=true) @RequestParam String password,
	                                 @ApiParam(value="角色, 1为消费者, 2为商家",allowableValues="1,2",required=true, example="1") @RequestParam @Range(min=1,max=2,message="roleId只能是1或2") Integer roleId){
		String jwt = userService.register(username,password,roleId);
		return ApiResponse.success(Map.of("token",jwt));
	}

	@ApiOperation("登录")
	@PostMapping("/login")
	public ApiResponse<Map> login(@ApiParam(value="用户名", required=true) @RequestParam String username,
	                              @ApiParam(value="密码", required=true) @RequestParam String password){
		String jwt = userService.login(username,password);
		return ApiResponse.success(Map.of("token",jwt));
	}
}
