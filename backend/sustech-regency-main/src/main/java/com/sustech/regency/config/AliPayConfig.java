package com.sustech.regency.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.alipay.api.AlipayConstants.FORMAT_JSON;

@Configuration
public class AliPayConfig {
	@Value("${alipay.app-id}")
	private String appId;
	@Value("${alipay.merchant-private-key}")
	private String privateKey;
	@Value("${alipay.alipay-public-key}")
	private String alipayPublicKey;
	@Value("${alipay.sign-type}")
	private String signType;
	@Value("${alipay.charset}")
	private String charset;
	@Value("${alipay.gateway-url}")
	private String gatewayUrl;

	@Bean
	public AlipayClient alipayClient(){
		return new DefaultAlipayClient(gatewayUrl,appId,privateKey,FORMAT_JSON,charset,alipayPublicKey,signType);
	}
}
