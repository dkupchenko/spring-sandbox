package info.kupchenko.sandbox.spring.vertx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация OnDeployError применима к методам вертикали, которые вызываются при неуспешном деплое вертикали
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unused")
public @ interface OnDeployError {
}
