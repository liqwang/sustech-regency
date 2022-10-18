package com.sustech.regency.controller;

import com.sustech.regency.model.param.FindPasswordParam;
import com.sustech.regency.model.param.LoginParam;
import com.sustech.regency.model.param.RegisterParam;
import com.sustech.regency.service.UserService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

import static com.sustech.regency.util.VerificationUtil.judge;

@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@ApiOperation("发送验证码到邮箱")
	@PostMapping("/send-verification-code")
	public ApiResponse sendVerificationCode(@ApiParam(required=true)
	                                        @Email(message = "邮箱格式错误")
											@NotEmpty(message = "邮箱不能为空")
	                                        @RequestParam String email){
		userService.sendVerificationCode(email);
		return ApiResponse.success();
	}

	@ApiOperation(value = "注册",notes = "返回token以及用户的详细信息")
	@PostMapping("/register")
	public ApiResponse<Map<String,Object>> register (@Validated @RequestBody RegisterParam registerParam){
		judge(registerParam.getPassword());
		Map<String,Object> map=userService.register(registerParam.getVerificationCode(),
													registerParam.getEmail(),
													registerParam.getUsername(),
													registerParam.getPassword(),
													registerParam.getRoleId());
		return ApiResponse.success(map);
	}

	@ApiOperation("找回密码")
	@PostMapping("/find-password")
	public ApiResponse findPassword(@Validated @RequestBody FindPasswordParam findPasswordParam){
		judge(findPasswordParam.getNewPassword());
		userService.findPassword(findPasswordParam.getVerificationCode(),
								 findPasswordParam.getEmail(),
								 findPasswordParam.getNewPassword());
		return ApiResponse.success();
	}

	@ApiOperation(value = "登录",notes = "返回token以及用户的详细信息")
	@PostMapping("/login")
	public ApiResponse<Map<String,Object>> login(@Validated @RequestBody LoginParam loginParam){
		Map<String, Object> map = userService.login(loginParam.getUsernameOrEmail(), loginParam.getPassword());
		return ApiResponse.success(map);
	}
}
