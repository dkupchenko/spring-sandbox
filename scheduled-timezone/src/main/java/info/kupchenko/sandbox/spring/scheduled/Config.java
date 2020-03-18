package info.kupchenko.sandbox.spring.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * The Config ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 12.03.2020
 * Last review on 12.03.2020
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "info.kupchenko.sandbox.spring.scheduled")
public class Config {
    @Bean
    @SuppressWarnings("unused")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(9);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }
}
