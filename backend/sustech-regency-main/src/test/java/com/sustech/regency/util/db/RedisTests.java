package com.sustech.regency.util.db;

import com.sustech.regency.db.util.Redis;
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
