package com.sustech.regency.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginParam {
	@ApiModelProperty(value = "用户名", required = true, example = "Sakura")
	@NotEmpty(message = "Username shouldn't be null")
	private String username;

	@ApiModelProperty(required = true, example = "***********")
	@Size(min=8, max=30, message = "密码需要为8-30位")
	@NotEmpty(message = "Password shouldn't be null")
	private String password;
}
