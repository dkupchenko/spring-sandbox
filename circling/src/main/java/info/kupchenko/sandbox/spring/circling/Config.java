package info.kupchenko.sandbox.spring.circling;

import info.kupchenko.summer.context.annotation.AutoStartupLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * The Config ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 12.03.2020
 * Last review on 12.03.2020
 */
@Configuration
@AutoStartupLifecycle
@EnableAsync
@ComponentScan(basePackages = "info.kupchenko.sandbox.spring.circling")
public class Config {
    @Bean
    @SuppressWarnings("unused")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(9);
        executor.setQueueCapacity(25);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
