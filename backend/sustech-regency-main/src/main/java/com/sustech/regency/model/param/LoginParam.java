package com.sustech.regency.model.param;

import com.sustech.regency.validator.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginParam {
	@ApiModelProperty(value = "用户名或邮箱", required = true)
	@NotEmpty(message = "Username or email shouldn't be null")
	private String usernameOrEmail;

	@ApiModelProperty(required = true, example = "***********")
	@Password
	private String password;
}
