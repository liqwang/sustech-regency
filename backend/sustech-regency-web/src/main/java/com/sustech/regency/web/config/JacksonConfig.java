package com.sustech.regency.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigInteger;

@Configuration
public class JacksonConfig {

	/**
	 * 由于前端JS的number为53位，后端Java的long为64位<p>
	 * 所以后端需要将long类型的数据转为string的Json返回<p>
	 * <a href="https://cloud.tencent.com/developer/article/1703457">long类型id返回前端之后的精度丢失问题</a>
	 */
	@Bean
	@Primary //有相同类型的多个Bean时，优先注入这个Bean
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder){
		return builder.createXmlMapper(false).build()
			  .registerModule(new SimpleModule()
								 .addSerializer(Long.class, ToStringSerializer.instance)
								 .addSerializer(Long.TYPE , ToStringSerializer.instance)
								 .addSerializer(BigInteger.class, ToStringSerializer.instance));
	}
}