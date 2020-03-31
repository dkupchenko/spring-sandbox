package info.kupchenko.sandbox.spring.vertx.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация VertX используется в файле Java-конфигурации Spring для:
 * 1) декларации использования бина Vertx
 * 2) деплоя вертикалей, аннтотированых с помощью @Verticle
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(VertxAnnotationConfiguration.class)
@SuppressWarnings("unused")
public @ interface VertX {
}
