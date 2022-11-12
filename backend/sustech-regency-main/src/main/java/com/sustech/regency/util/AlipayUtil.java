package com.sustech.regency.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.alipay.api.AlipayConstants.FORMAT_JSON;

@Component
public class AlipayUtil {

	@Value("${alipay.app-id}")
	private String appId;
	@Value("${alipay.merchant-private-key}")
	private String privateKey;
	@Value("${alipay.alipay-public-key}")
	private String alipayPublicKey;
	@Value("${alipay.notify-url}")
	private String notifyUrl;
	@Value("${alipay.sign-type}")
	private String signType;
	@Value("${alipay.charset}")
	private String charset;
	@Value("${alipay.gateway-url}")
	private String gatewayUrl;
	@Value("${alipay.pay-timeout}")
	private String payTimeout;

	/**
	 * @return 支付宝返回的二维码URL
	 */
	@SneakyThrows
	public String qrCodePay(AlipayTradePrecreateModel payInfo){
		AlipayClient alipayClient=getAlipayClient();
		AlipayTradePrecreateRequest payRequest = new AlipayTradePrecreateRequest();
		payRequest.setBizModel(payInfo); //设置支付信息
		payRequest.setNotifyUrl(notifyUrl); //异步回调地址
		AlipayTradePrecreateResponse payResponse;
		payResponse=alipayClient.execute(payRequest);
		Map<String,Object> responseBodyMap = new ObjectMapper().readValue(payResponse.getBody(), new TypeReference<>(){});
		//noinspection unchecked
		return ((Map<String, String>)responseBodyMap.get("alipay_trade_precreate_response")).get("qr_code");
	}

	private AlipayClient getAlipayClient(){
		return new DefaultAlipayClient(gatewayUrl,appId,privateKey,FORMAT_JSON,charset,alipayPublicKey,signType);
	}

	public String getPayTimeout(){
		return payTimeout;
	}
}
