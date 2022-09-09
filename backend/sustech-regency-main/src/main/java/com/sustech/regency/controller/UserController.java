package com.sustech.regency.controller;

import com.sustech.regency.service.UserService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@ApiOperation("注册")
	@PostMapping("/register")
	public ApiResponse<Map> register(@ApiParam(value="用户名", required=true) @RequestParam String name,
	                                 @ApiParam(value="密码", required=true) @RequestParam String password){
		String jwt = userService.register(name,password);
		return ApiResponse.success(Map.of("token",jwt));
	}

	@ApiOperation("登录")
	@PostMapping("/login")
	public ApiResponse<Map> login(@ApiParam(value="用户名", required=true) @RequestParam String name,
	                              @ApiParam(value="密码", required=true) @RequestParam String password){
		String jwt = userService.login(name,password);
		return ApiResponse.success(Map.of("token",jwt));
	}
}
