package com.sustech.regency.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationParam {
    @ApiModelProperty
    String province;
    @ApiModelProperty
    String city;
    @ApiModelProperty
    String region;
    @ApiModelProperty
    String name;
}
