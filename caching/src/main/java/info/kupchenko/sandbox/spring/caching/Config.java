package info.kupchenko.sandbox.spring.caching;

import info.kupchenko.sandbox.spring.caching.shop.*;
import info.kupchenko.summer.context.annotation.AutoStartupLifecycle;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.time.LocalDateTime;

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
@EnableCaching
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
     * Бин БД H2
     * @return dataSource
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:init_db.sql")
                .build();
    }

    /**
     * Бин JdbcTemplate
     * @return JdbcTemplate
     */
    @Bean
    @SuppressWarnings("unused")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * Бин CaffeineCacheManager
     * @return CacheManager
     */
    @Bean
    @SuppressWarnings("unused")
    public CacheManager cacheManager() {
        return new CaffeineCacheManager("product");
    }

    /**
     * Бин Продукта для инжектирования в Ассортимент
     * @param id идентификатор продукта
     * @param price цена продукта
     * @return экземпляр Продкута
     */
    @Bean
    @Scope("prototype")
    public Product product(long id, float price) {
        return new ProductImpl(id, price);
    }

    /**
     * Бин Ассортимента для инжектирования в него экземпляров Продукта
     * @return ассортимент
     */
    @Bean
    @SuppressWarnings("unused")
    public Stock stock() {
        return new StockImpl(jdbcTemplate()) {
            @Override
            public Product getProduct(long id, float price) {
                return product(id, price);
            }
        };
    }

    /**
     * Бин Заказа для индектирования его в бин Покупателя
     * @param buyerId идентификатор покупателя
     * @param dt время заказа
     * @return экземпляр Заказа
     */
    @Bean
    @Scope("prototype")
    public Order order(long buyerId, LocalDateTime dt) {
        return new OrderImpl(buyerId, dt);
    }

    /**
     * Бин Покупателя для инжектирования в бин Фабрики Покупателей
     * @param shop Бин Магазина
     * @return экземпляр Покупателя
     */
    @Bean
    @Scope("prototype")
    Buyer buyer(Shop shop) {
        return new BuyerImpl(shop) {
            @Override
            public Order getOrder(long buyerId, LocalDateTime dt) {
                return order(buyerId, dt);
            }
        };
    }

    /**
     * Бин Фабрики Покупателей для инжектирования в него экземпляров Покупателей
     * @param shop Бин Магазина
     * @return экземпляр Фабрики Покупателей
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
