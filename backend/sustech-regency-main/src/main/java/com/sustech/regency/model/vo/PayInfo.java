package com.sustech.regency.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建订单后的有关后续支付的响应信息
 */
@Data
@Accessors(chain = true)
public class PayInfo {

	@ApiModelProperty("支付二维码的Base64编码，过期时间15分钟")
	private String base64QrCode;

	@ApiModelProperty("前端需要监听的WebSocket，用于获知用户是否付款")
	private String webSocketUrl;
}
