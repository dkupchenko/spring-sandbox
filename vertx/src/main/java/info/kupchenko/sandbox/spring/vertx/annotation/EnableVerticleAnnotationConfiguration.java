package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Класс VertxAnnotationConfiguration является дополнением к Java-конфигурации и декларирует дополнительные бины
 * для функционирования аннотации @EnableVerticle
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
@Configuration
@ComponentScan(basePackages = "info.kupchenko.sandbox.spring.vertx.annotation")
public class EnableVerticleAnnotationConfiguration {
    /**
     * бин ядра Vertx
     *
     * @return бин
     */
    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }
}
