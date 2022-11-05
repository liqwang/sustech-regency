package com.sustech.regency.model.param;

import com.sustech.regency.validator.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPasswordParam {
	@ApiModelProperty(value="验证码",required=true,example="\"114514\"")
	@Size(min=6,max=6,message="必须为6位")
	String verificationCode;

	@ApiModelProperty(required=true)
	@Email @NotEmpty
	String email;

	@ApiModelProperty(required = true)
	@Password
	String newPassword;
}
