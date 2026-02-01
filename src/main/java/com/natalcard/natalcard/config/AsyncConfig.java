package com.natalcard.natalcard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Async configuration for handling concurrent natal chart calculations
 * Allows processing multiple requests simultaneously without blocking
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Thread pool for natal chart calculations
     * - Core pool: 10 threads (always active)
     * - Max pool: 50 threads (scales up to 50 concurrent requests)
     * - Queue capacity: 100 (buffers overflow requests)
     * - Thread name: "NatalChart-Async-"
     */
    @Bean(name = "natalChartExecutor")
    public Executor natalChartExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Core pool size - minimum threads always alive
        executor.setCorePoolSize(10);

        // Max pool size - maximum concurrent threads
        executor.setMaxPoolSize(50);

        // Queue capacity - buffer for waiting requests
        executor.setQueueCapacity(100);

        // Thread naming pattern
        executor.setThreadNamePrefix("NatalChart-Async-");

        // Wait for tasks to complete on shutdown
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);

        // Reject policy: CallerRunsPolicy
        // If pool is full, execute in caller's thread (graceful degradation)
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }

    /**
     * Thread pool for external API calls (location services)
     * - Core pool: 5 threads
     * - Max pool: 20 threads
     * - Lower capacity to respect rate limits of external services
     */
    @Bean(name = "locationServiceExecutor")
    public Executor locationServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("Location-Async-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }
}
