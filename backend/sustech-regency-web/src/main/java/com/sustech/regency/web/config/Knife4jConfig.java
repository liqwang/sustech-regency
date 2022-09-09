package com.sustech.regency.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {
	@Bean
	public Docket docket(){
		return new Docket(DocumentationType.SWAGGER_2)
				  .apiInfo(new ApiInfoBuilder()
							  .title("SUSTech-Regency API")
							  .version("v1.0")
							  .termsOfServiceUrl("https://quanquancho.com")
//							  .contact(new Contact("QuanQuan","https://github.com/QuanQuan-CHO","12011619@mail.sustech.edu.cn"))
							  .license("MIT license")
							  .licenseUrl("https://mit-license.org")
							  .build())
				  .select()
				  .apis(RequestHandlerSelectors.basePackage("com.sustech.regency.controller"))
				  .build();
	}
}
