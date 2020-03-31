package info.kupchenko.sandbox.spring.vertx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация DeployOptions применима к полю, которое инспользуется автодеплоем в качестве опций.
 * КОНТРАКТ:
 * 1) в случае наличия в классе нескольких полей, помеченных данной аннотацией, использоваться будет
 *    случайное единственное поле
 * 2) проверка соответствия типа не выполняется
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unused")
public @ interface DeployOptions {
}
