package com.smsmode.mailing.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 * <p>Created 07 Nov 2024
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * {@inheritDoc}
     *
     * <p>Creates and configures a custom {@link Executor} bean for asynchronous task execution. The
     * executor is a {@link ThreadPoolTaskExecutor} with specific core pool size, max pool size, queue
     * capacity, and thread name prefix.
     *
     * @return The configured {@link Executor} bean for asynchronous task execution.
     */
    @Override
    @Bean()
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("mailing-thread-");
        executor.initialize();
        return executor;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns a {@link SimpleAsyncUncaughtExceptionHandler} to handle uncaught exceptions that
     * occur during asynchronous execution.
     *
     * @return The {@link AsyncUncaughtExceptionHandler} for handling uncaught exceptions.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
