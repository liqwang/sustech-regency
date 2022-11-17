package com.sustech.regency.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AlipayUtil {

	@Value("${alipay.notify-url}")
	private String notifyUrl;
	@Resource
	private AlipayClient alipayClient;

	/**
	 * @return 支付宝返回的二维码URL
	 */
	public String qrCodePay(AlipayTradePrecreateModel payInfo){
		AlipayTradePrecreateRequest payRequest = new AlipayTradePrecreateRequest();
		payRequest.setBizModel(payInfo); //设置支付信息
		payRequest.setNotifyUrl(notifyUrl); //异步回调地址
		return getResponse(payRequest)
			  .getByPath("alipay_trade_precreate_response.qr_code",String.class);
	}

	public void refund(AlipayTradeRefundModel refundInfo){
		AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
		refundRequest.setBizModel(refundInfo);
		//Todo
	}

	/**
	 * @return Response的body的 {@link JSONObject}
	 */
	@SneakyThrows(AlipayApiException.class)
	public <T extends AlipayResponse> JSONObject getResponse(AlipayRequest<T> request){
		return JSONUtil.parseObj(alipayClient.execute(request).getBody());
	}
}
