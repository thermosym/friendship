package com.demo.friendship.configuration;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfiguration {

    @Value("${thread.pool.size.core:2}")
    int corePoolSize;

    @Value("${thread.pool.size.max:10}")
    int maxPoolSize;

    @Bean
    @Qualifier("SHARED_TASK_EXECUTOR")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadFactory(new BasicThreadFactory.Builder().namingPattern("common-shared-task-executor").build());
        pool.setCorePoolSize(corePoolSize);
        pool.setMaxPoolSize(maxPoolSize);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

}
