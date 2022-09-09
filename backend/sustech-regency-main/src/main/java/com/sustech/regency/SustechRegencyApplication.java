package com.sustech.regency;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sustech.regency.db.dao")
@SpringBootApplication
public class SustechRegencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SustechRegencyApplication.class, args);
	}

}
