package info.kupchenko.sandbox.spring.vertx;

import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import info.kupchenko.sandbox.spring.vertx.entities.RateMessageCodec;
import info.kupchenko.summer.context.annotation.AutoStartupLifecycle;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Класс Config описывает конфигурацию приложения
 *
 * @author Dmitry Kupchenko
 * @version 2.0
 * Created on 28.03.2020
 * Last review on 30.03.2020
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "info.kupchenko.sandbox.spring.vertx")
@PropertySource("classpath:application.properties")
@AutoStartupLifecycle
@SuppressWarnings("unused")
public class Config {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(Config.class);

    @Bean
    public Vertx vertx() {
        log.debug("Before creating Vertx bean");
        Vertx vertx = Vertx.vertx();
        vertx.eventBus().registerDefaultCodec(Rate.class, rateMessageCodec());
        log.debug("Vertx bean is created");
        return vertx;
    }

    @Bean
    public RateMessageCodec rateMessageCodec() {
        return new RateMessageCodec();
    }
}
