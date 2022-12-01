package com.sustech.regency.db.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RedisTests {

	@Resource
	private Redis redis;
	@Test
	void testNull(){
		redis.setObject("key",null,15);
	}
}
