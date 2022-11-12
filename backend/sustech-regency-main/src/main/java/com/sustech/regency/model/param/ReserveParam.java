package com.sustech.regency.model.param;

import com.sustech.regency.web.annotation.DateParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class ReserveParam {

	@ApiModelProperty(value = "房间Id",required = true, example = "1")
	@NotNull
	Integer roomId;

	@ApiModelProperty(value = "预定开始时间",required = true, example = "2022-12-10")
	@DateParam
	@NotNull
	Date startTime;

	@ApiModelProperty(value = "预定结束时间",required = true, example = "2022-12-13")
	@DateParam
	@NotNull
	Date endTime;

	@ApiModelProperty(value = "同住人信息列表")
	@NotEmpty @Valid
	List<Cohabitant> cohabitants;
}
