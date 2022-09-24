package com.sustech.regency.controller;

import com.sustech.regency.model.param.LoginParam;
import com.sustech.regency.service.UserService;
import com.sustech.regency.util.VerificationUtil;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Map;

@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@ApiOperation("发送验证码到邮箱")
	@PostMapping("/send-verification-code")
	public ApiResponse sendVerificationCode(@ApiParam(required=true)
	                                        @Email(message = "邮箱格式错误")
	                                        @RequestParam String email){
		userService.sendVerificationCode(email);
		return ApiResponse.success();
	}

	@ApiOperation("注册")
	@PostMapping("/register")
	public ApiResponse<Map> register(@ApiParam(value="角色, 1为消费者, 2为商家",allowableValues="1,2",required=true,example="1")
	                                 @Range(min=1,max=2,message="roleId只能是1或2")
	                                 @RequestParam Integer roleId,

	                                 @ApiParam(value="验证码",required=true,example="114514")
	                                 @Size(min=6,max=6,message="验证码必须为6位")
	                                 @RequestParam String verificationCode,

	                                 @ApiParam(required=true)
	                                 @Email(message = "邮箱格式错误")
	                                 @RequestParam String email,

	                                 @Validated @RequestBody LoginParam loginParam){
		VerificationUtil.judge(loginParam.getPassword());
		String jwt = userService.register(verificationCode,email,loginParam.getUsername(),loginParam.getPassword(),roleId);
		return ApiResponse.success(Map.of("token",jwt));
	}

	@ApiOperation("找回密码")
	@PostMapping("/find-password")
	public ApiResponse findPassword(@ApiParam(value="验证码",required=true,example="114514")
		                            @Size(min=6,max=6,message="验证码必须为6位")
		                            @RequestParam String verificationCode,

	                                @ApiParam(required=true)
	                                @Email(message = "邮箱格式错误")
	                                @RequestParam String email,

	                                @ApiParam(required = true)
	                                @NotEmpty(message = "Username shouldn't be null")
	                                @RequestParam String username,

	                                @ApiParam(required = true)
	                                @Size(min=8,max=30,message = "密码需要为8-30位")
	                                @RequestParam String newPassword){
		VerificationUtil.judge(newPassword);
		userService.findPassword(verificationCode,email,username,newPassword);
		return ApiResponse.success();
	}

	@ApiOperation("登录")
	@PostMapping("/login")
	public ApiResponse<Map> login(@Validated @RequestBody LoginParam loginParam){
		String jwt = userService.login(loginParam.getUsername(),loginParam.getPassword());
		return ApiResponse.success(Map.of("token",jwt));
	}
}
