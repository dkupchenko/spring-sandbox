package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс VerticalScopeConfigurator обеспечивает работу дополнительного SCOPE "verticle"
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 01.04.2020
 * Last review on 01.04.2020
 */
@Component
class VerticleScopeConfigurator implements Scope {
    /**
     * for logging
     */
    private final Log log = LogFactory.getLog(getClass());
    /**
     * кэш-мапа пар /<имя бина, бин-вертикаль/>
     */
    private final Map<String, Object> cachedBeans = new HashMap<>();

    /**
     * возвращает новый бин для вертикалей, которые уже есть в кэше; хранит первые экземпляры бинов в кэше
     *
     * @param beanName
     * @param objectFactory
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    @NonNull
    public Object get(@NonNull String beanName, ObjectFactory<?> objectFactory) throws IllegalArgumentException {
        log.debug(String.format("working with bean '%s'", beanName));
        Object bean = objectFactory.getObject();
        Class<?> beanClass = bean.getClass();
        if(!beanClass.isAnnotationPresent(Verticle.class))
            throw new IllegalArgumentException(String.format("bean '%s' have to be annotated with @Verticle", beanName));
        if(bean instanceof AbstractVerticle) {
            // наш случай
            if (!cachedBeans.containsKey(beanName)) {
                // новый бин с необходимыми органичениями, будем кэшировать
                cachedBeans.put(beanName, bean);
            } else {
                // бин не новый
                DeploymentOptions options = AnnotatedVerticleHelper.getDeploymentOptions(bean);
                // если это обычная вертикаль без всяких наворотов, то вернём из кэша
                if(options == null || !options.isWorker())
                    return cachedBeans.get(beanName);
            }
            return bean;
        }
        log.error(String.format("bean '%s' is not instance of AbstractVerticle", beanName));
        throw new IllegalArgumentException(String.format("bean '%s' is not instance of AbstractVerticle", beanName));
    }

    @Override
    public Object remove(@NonNull String s) {
        return null;
    }

    @Override
    public void registerDestructionCallback(@NonNull String s, @NonNull Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(@NonNull String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    Map<String, Object> getCachedBeans() {
        return cachedBeans;
    }
}
