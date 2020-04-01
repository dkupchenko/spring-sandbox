package info.kupchenko.sandbox.spring.vertx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация Verticle маркурует бины, которые необходимо автоматически продеплоить. Если имя вертикали не указано,
 * то будет использовано имя бина
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 01.03.2020
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unused")
public @ interface Verticle {
    /**
     * имя вертикали для деплоя
     *
     * @return имя вертикали
     */
    String name() default "";
}
