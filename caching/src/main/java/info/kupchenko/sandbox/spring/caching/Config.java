package info.kupchenko.sandbox.spring.caching;

import info.kupchenko.sandbox.spring.caching.shop.*;
import info.kupchenko.summer.context.annotation.AutoStartupLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Класс Config является Java-аннотацией конфигурации контекста приложения
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 19.03.2020
 * Last review on 19.03.2020
 */
@Configuration
@AutoStartupLifecycle
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = "info.kupchenko.sandbox.spring.caching")
public class Config {
    /**
     * Бин пула потоков для Покупателей, т.к. вся полезная нагрузка выполняется ими в отдельных потоках
     * @return синглтон Пула потоков
     */
    @Bean
    @SuppressWarnings("unused")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(130);
        executor.setQueueCapacity(20);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    /**
     * Бин Товара, описывается здесь для реализации DI в бине Магазина
     * @return новый Товар
     */
    @Bean
    @Scope("prototype")
    Product product() {
        return new ProductImpl();
    }

    /**
     * Бин Магазина, производит DI Товаров в Магазин
     * @return синглтон Магазин
     */
    @Bean
    @SuppressWarnings("unused")
    Stock stock() {
        return new StockImpl() {
            @Override
            public Product getProduct() {
                return product();
            }
        };
    }

    /**
     * Бин Покупателя, описывается здесь для реализации DI в бине Фабрики Покупателей
     * @param shop Бин Магазина, инжектируется Spring'ом
     * @return новый Покупатель
     */
    @Bean
    @Scope("prototype")
    Buyer buyer(Shop shop) {
        return new BuyerImpl(shop);
    }

    /**
     * Бин Фабрики Покупателей, производит DI Покупателей в Фабрику
     * @param shop Бин Магазина, инжектируется Spring'ом
     * @return синглтон Фабрика Покупателей
     */
    @Bean
    @SuppressWarnings("unused")
    BuyersSet buyersSet(Shop shop) {
        return new BuyersSetImpl() {
            public Buyer getBuyer() {
                return buyer(shop);
            }
        };
    }
}
