package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Класс SpringVerticleFactory является адаптером между фреймворками Spring и Vert.x, позволяя деплоить вертикали
 * с помощью механизма VerticleFactory на основании ApplicationContext
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 01.04.2020
 * Last review on 01.04.2020
 */
@Component
@SuppressWarnings("unused")
class SpringVerticleFactoryImpl implements SpringVerticleFactory, ApplicationContextAware {
    /**
     * for logging
     */
    private final Log log = LogFactory.getLog(getClass());
    /**
     * factory prefix
     */
    private static final String PREFIX = "spring";
    /**
     * Spring application context
     */
    private ApplicationContext springContext;
    /**
     * AnnotatedVerticleMap bean
     */
    private final AnnotatedVerticleMap verticles;

    SpringVerticleFactoryImpl(AnnotatedVerticleMap verticles) {
        this.verticles = verticles;
    }

    /**
     * возвращает бин-вертикаль Spring'а по его имени, запрашивая данный бин по имени у контекста;
     * бин должен существовать в контексте на момент вызова метода и являться наследником AbstractVerticle;
     * ВАЖНО: метод модно вызывать только на этапе автодеплоя вертикалей, когда VerticleBeanHolder уже
     * содержит по одному экземпляру вертикалей
     *
     * @param beanName имя бина
     * @return экземпляр бина
     * @throws IllegalArgumentException если бин не существует или не наследует от AbstractVerticle
     */
    @Override
    public AbstractVerticle getVerticleForBean(String beanName) throws IllegalArgumentException {
        log.debug(String.format("getting Verticle instance for bean name '%s'", beanName));
        if(!springContext.containsBean(beanName))
            throw new IllegalArgumentException(String.format("Bean '%s' is not found in Spring context", beanName));
        Object bean = springContext.getBean(beanName);
        if(bean instanceof AbstractVerticle) {
            return (AbstractVerticle) bean;
        } else {
            throw new IllegalArgumentException(String.format("Bean '%s' is not instance of Verticle", beanName));
        }
    }
    /**
     * Stores Spring ApplicationContext
     * ApplicationContextAware implementation
     *
     * @param springContext context to be provided by Spring
     * @throws BeansException check this out later
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext springContext) throws BeansException {
        this.springContext = springContext;
    }

    /**
     * Returns prefix that if used for verticles deployment process
     * VerticleFactory implementation
     *
     * @return prefix like "java", "js" etc; default value if "spring"
     */
    @Override
    public String prefix() {
        return PREFIX;
    }

    /**
     * creates verticle based on Spring context
     * VerticleFactory implementation
     *
     * @param verticleNameWithPrefix name of verticle
     * @param classLoader classLoader for verticle instantiation
     * @return Verticle
     * @throws IllegalArgumentException if bean is not found in AnnotatedVerticleMap or bean is not instance of Verticle
     */
    @Override
    public Verticle createVerticle(String verticleNameWithPrefix, ClassLoader classLoader) throws IllegalArgumentException {
        log.debug(String.format("creating Verticle instance for name '%s'", verticleNameWithPrefix));
        String verticleName = VerticleFactory.removePrefix(verticleNameWithPrefix);
        if(!verticles.containsKey(verticleName))
            throw new IllegalArgumentException(String.format("Can't find data for verticle '%s' in AnnotatedVerticleMap",
                    verticleNameWithPrefix));
        AnnotatedVerticle verticle = verticles.get(verticleName);
        String beanName = verticle.getBeanName();
        return getVerticleForBean(beanName);
    }
}
