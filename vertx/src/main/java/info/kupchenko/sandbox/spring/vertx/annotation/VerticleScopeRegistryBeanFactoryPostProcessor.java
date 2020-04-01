package info.kupchenko.sandbox.spring.vertx.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Класс VerticleScopeRegistryBeanFactoryPostProcessor регистрирует дополнительный @Scope 'verticle',
 * который создаёт и кэширует по одному инстансу каждого класса, маркированного @Verticle и являющегося
 * наследником AbstractVerticle. Дополнительные инстансы создаются для случаев в опциях деплоя:
 * 1) setWorker(true)
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 01.04.2020
 * Last review on 01.04.2020
 */
@Component
@SuppressWarnings("unused")
public class VerticleScopeRegistryBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerScope("verticle", new VerticleScopeConfigurator());
    }
}
