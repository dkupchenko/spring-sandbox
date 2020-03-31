package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;

/**
 * Класс VertxAnnotationConfiguration является дополнением к Java-конфигурации и декларирует дополнительные бины
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
@SuppressWarnings("unused")
public class VertxAnnotationConfiguration {
    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public VertXHandlerBeanPostProcessor vertXHandlerBeanPostProcessor() {
        return new VertXHandlerBeanPostProcessor();
    }

    @Bean
    public VertXDeployerContextListener vertXDeployerContextListener() {
        return new VertXDeployerContextListener();
    }
}
