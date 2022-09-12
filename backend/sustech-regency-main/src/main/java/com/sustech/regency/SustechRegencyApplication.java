package com.sustech.regency;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sustech.regency.db.dao")
@SpringBootApplication
public class SustechRegencyApplication {
	//Todo:部分公开代码而不丢失提交历史:https://stackoverflow.com/questions/44261626/github-make-a-private-repository-partially-public
	public static void main(String[] args) {
		SpringApplication.run(SustechRegencyApplication.class, args);
	}

}
