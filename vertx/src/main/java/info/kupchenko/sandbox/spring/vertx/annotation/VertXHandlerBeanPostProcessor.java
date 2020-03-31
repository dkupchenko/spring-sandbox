package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * The VertXHandlerBeanPostProcessor ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
public class VertXHandlerBeanPostProcessor implements BeanPostProcessor {

    private final Log logger = LogFactory.getLog(getClass());

    private Map<String, VerticleHelper> verticles = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        // if it is @Verticle bean
        if(beanClass.isAnnotationPresent(Verticle.class)) {
            if(bean instanceof AbstractVerticle) {
                VerticleHelper verticle = new VerticleHelper(bean, beanName);
                // save data to map
                verticles.put(beanName, verticle);
            } else {
                logger.warn(String.format("VertXHandlerBeanPostProcessor: bean %s is annotated with @Verticle but doesn't extend AbstractVerticle class", beanName));
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(@NonNull Object bean, String beanName) throws BeansException {
        if(verticles.containsKey(beanName))
            verticles.get(beanName).setBean((AbstractVerticle) bean);
        return bean;
    }

    public Map<String, VerticleHelper> getVerticles() {
        return verticles;
    }
}
