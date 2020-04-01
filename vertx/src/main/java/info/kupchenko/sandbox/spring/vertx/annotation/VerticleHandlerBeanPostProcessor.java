package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Класс VertXHandlerBeanPostProcessor - это бин типа BeanPostProcessor Spring'а,
 * который собирает информацию об аннотированных вертикалях для последующего автодеплоя во время конструирования контекста
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 01.04.2020
 */
@Component
@SuppressWarnings("unused")
public class VerticleHandlerBeanPostProcessor implements BeanPostProcessor {
    /**
     * логгирование
      */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * бин AnnotatedVerticleMap
     */
    private final AnnotatedVerticleMap verticles;

    /**
     * конструктор для инжекции зависимостей
     *
     * @param verticles бин AnnotatedVerticleMap
     */
    public VerticleHandlerBeanPostProcessor(AnnotatedVerticleMap verticles) {
        this.verticles = verticles;
    }

    /**
     * Вызывается Spring'ом перед инициализацией каждого бина
     * BeanPostProcessor implementation
     *
     * @param bean бин
     * @param beanName имя бина
     * @return немодифицированный исходный бин
     */
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();
        // если @Verticle, то проверим на AbstractVerticle и добавим в хранилище
        if(beanClass.isAnnotationPresent(Verticle.class)) {
            if(bean instanceof AbstractVerticle) {
                // аннотированная вертикаль добавляется к мапе
                AnnotatedVerticle verticle = new AnnotatedVerticle((AbstractVerticle) bean, beanName);
                verticles.put(verticle.getName(), verticle);
            } else {
                // вертикаль, не наследуемая от AbstractVerticle (не принимается к последующему деплою)
                logger.warn(String.format("bean %s is annotated with @Verticle but doesn't extend AbstractVerticle class", beanName));
            }
        }
        // бин возвращаем в неизменном виде
        return bean;
    }

    /**
     * Вызывается Spring'ом после инициализации каждого бина, заглушка
     * BeanPostProcessor implementation
     *
     * @param bean бин
     * @param beanName имя
     * @return немодифицированный исходный бин
     */
    public Object postProcessAfterInitialization(@NonNull Object bean, String beanName){
        return bean;
    }
}
