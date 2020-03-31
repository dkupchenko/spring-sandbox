package info.kupchenko.sandbox.spring.vertx;

import info.kupchenko.sandbox.spring.vertx.annotation.VertX;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import info.kupchenko.sandbox.spring.vertx.entities.RateMessageCodec;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Класс Config описывает конфигурацию приложения
 *
 * @author Dmitry Kupchenko
 * @version 3.0
 * Created on 28.03.2020
 * Last review on 31.03.2020
 */
@Configuration
@ComponentScan(basePackages = "info.kupchenko.sandbox.spring.vertx")
@PropertySource("classpath:application.properties")
@VertX
@SuppressWarnings("unused")
public class Config {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(Config.class);

    /**
     * Регистрация кодека котировок валют для шины (как таковой бин не нужен,
     * просто конфиг - удобное место, чтобы произвести регистрацию)
     *
     * @param vertx бин vertx, создаётся аннотацией @VertX
     * @return бин кодека котировок валют
     */
    @Bean
    public RateMessageCodec rateMessageCodec(Vertx vertx) {
        RateMessageCodec codec = new RateMessageCodec();
        vertx.eventBus().registerDefaultCodec(Rate.class, codec);
        log.debug("RateMessageCodec registered");
        return codec;
    }
}
