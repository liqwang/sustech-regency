package com.sustech.regency.db.config;

import com.sustech.regency.db.util.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<Object,Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(serializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(serializer);

		template.afterPropertiesSet();
		return template;
	}
}
