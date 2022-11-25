package com.sustech.regency;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sustech.regency.db.dao")
@SpringBootApplication
public class SustechRegencyApplication {
	//Todo:秒杀设计https://mp.weixin.qq.com/s?__biz=Mzg3NzU5NTIwNg==&mid=2247493227&idx=1&sn=10e5064d7d224c69dce400e90cd44de6&chksm=cf223942f855b0541ada22a312e0d4ffbc99df463678247a0dede3ef16eb81e3344a4a54ceaf&token=1990771297&lang=zh_CN#rd
	//Todo:使用SpringSecurity进行API鉴权
	//Todo:微服务分离消费者/consumer和商家/merchant，使用Nginx反代隐藏端口
	//Todo:部分公开代码而不丢失提交历史:https://stackoverflow.com/questions/44261626/github-make-a-private-repository-partially-public
	public static void main(String[] args) {
		SpringApplication.run(SustechRegencyApplication.class, args);
	}
}
