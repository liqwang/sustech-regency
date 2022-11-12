package com.sustech.regency.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class Cohabitant {

	//Todo:其实这里应该使用严谨的身份证号校验@IdNumber
	@ApiModelProperty(value = "同住人身份证号", required = true, example = "114514200204181234")
	@NotEmpty @Size(min = 18, max = 18, message = "必须为18位") //Todo:封装固定长度@Length
	String idNumber;

	@ApiModelProperty(required = true, example = "张三")
	@NotEmpty @Size(min = 2)
	String name;
}
