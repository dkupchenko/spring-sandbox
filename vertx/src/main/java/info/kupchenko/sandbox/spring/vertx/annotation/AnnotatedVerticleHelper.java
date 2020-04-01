package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.DeploymentOptions;

import java.lang.reflect.Field;

/**
 * Класс AnnotatedVerticleHelper является хэлпером, в который вынесены функции по Java Reflection API
 * для обработки AnnotatedVerticle, используемые в других классах
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 01.04.2020
 * Last review on 01.04.2020
 */
class AnnotatedVerticleHelper {
    /**
     * возвращает опции для деплоя
     *
     * @return опции для деплоя
     */
    public static DeploymentOptions getDeploymentOptions(Object bean) {
        Class<?> beanClass = bean.getClass();
        DeploymentOptions options = null;
        for(Field field: beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(DeployOptions.class) && field.getType().isAssignableFrom(DeploymentOptions.class)) {
                try {
                    field.setAccessible(true);
                    options = (DeploymentOptions) field.get(bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return options;
    }
}
