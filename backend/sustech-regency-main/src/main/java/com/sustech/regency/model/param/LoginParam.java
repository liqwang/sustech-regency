package com.sustech.regency.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginParam {
	@ApiModelProperty(value = "用户名或邮箱", required = true)
	@NotEmpty(message = "Username or email shouldn't be null")
	private String usernameOrEmail;

	@ApiModelProperty(required = true, example = "***********")
	@Size(min=8, max=30, message = "密码需要为8-30位")
	@NotEmpty
	private String password;
}
