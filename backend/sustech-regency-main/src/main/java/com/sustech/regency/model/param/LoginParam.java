package com.sustech.regency.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginParam {
	@ApiModelProperty(value = "用户名", required = true, example = "Sakura")
	@NotEmpty(message = "Username shouldn't be null")
	private String username;

	@ApiModelProperty(required = true, example = "***********")
	@NotEmpty(message = "Password shouldn't be null")
	private String password;
}
