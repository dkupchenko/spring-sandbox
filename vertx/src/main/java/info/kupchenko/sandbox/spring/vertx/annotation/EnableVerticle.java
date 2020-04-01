package info.kupchenko.sandbox.spring.vertx.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация EnableVerticle используется в файле Java-конфигурации Spring для:
 * 1) добавления бина Vertx в контекст Spring'а
 * 2) добавления бина SpringVerticleFactory в контекст Spring'а и регистрации фабрики в Vertx
 * 3) деплоя вертикалей, аннтотированых с помощью @Verticle, @DeployOptions, @OnDeploySuccess и  @OnDeployError
 * При этом в вертекс добавляется фабрика SpringVerticleFactory для интерграции
 * деплоя вертикалей с конфигурацией бинов Spring'а
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 01.03.2020
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EnableVerticleAnnotationConfiguration.class)
@SuppressWarnings("unused")
public @ interface EnableVerticle {
}
