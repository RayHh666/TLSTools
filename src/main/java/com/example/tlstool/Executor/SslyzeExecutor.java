package com.example.tlstool.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SslyzeExecutor {

    // 主任务线程池（处理任务创建）
    @Bean(name = "mainTaskExecutor")
    public Executor mainTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("MainTask-");
        return executor;
    }

    // 子任务线程池（处理具体扫描）
    @Bean(name = "subTaskExecutor")
    public Executor subTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50); // 可扩展更多
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("SubScan-");
        return executor;
    }
}
