package com.sustech.regency.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * <a href="https://juejin.cn/post/6970558076642394142">CompletableFuture 实现异步任务</a><p>
 * <a href="https://cloud.tencent.com/developer/article/1704658">ForkJoinPool 与工作窃取机制</a>
 */
@Configuration
public class ThreadPoolConfig {

	@Bean
	public ThreadPoolExecutor threadPoolExecutor(){
		int cpuCores = Runtime.getRuntime().availableProcessors();
		int fixedPoolSize = 10*cpuCores; //IO密集型
		ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(fixedPoolSize,
				fixedPoolSize,
				1,
				TimeUnit.MINUTES,
				new LinkedBlockingQueue<>(cpuCores * 10000),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy());
		fixedThreadPool.prestartAllCoreThreads(); //核心线程预启动
		return fixedThreadPool;
	}
}
