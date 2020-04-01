package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Класс VerticleDeployerContextListener является получателем события о том, что контекст Spring'а собран полностью.
 * Единственная задача класса - получить событие и продеплоить все аннотированные вертикали
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 01.04.2020
 */
@Component
@SuppressWarnings("unused")
class VerticleDeployerContextListener implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * логирование
     */
    private final Log logger = LogFactory.getLog(getClass());
    /**
     * бин Vertx
     */
    private final Vertx vertx;
    /**
     * бин SpringVerticleFactory
     */
    private SpringVerticleFactory factory;
    /**
     * кэш пар /<имя бина, бин-вертикаль/> из VerticleScopeConfigurator
     */
    private final Map<String, Object> cachedBeans;
    /**
     * бин AnnotatedVerticleMap
     */
    private final AnnotatedVerticleMap verticles;

    /**
     * конструктор для инжекций
     *  @param vertx бин Vertx
     * @param factory бин SpringVerticleFactory
     * @param verticleScopeConfigurator бин VerticleScopeConfigurator
     * @param verticles бин AnnotatedVerticleMap
     */
    public VerticleDeployerContextListener(Vertx vertx, SpringVerticleFactory factory,
                                           VerticleScopeConfigurator verticleScopeConfigurator, AnnotatedVerticleMap verticles) {
        this.vertx = vertx;
        this.factory = factory;
        this.cachedBeans = verticleScopeConfigurator.getCachedBeans();
        this.verticles = verticles;
    }

    /**
     * Событие Spring о том, что его контекст собран
     * @param event событие
     */
    @Override
    public void onApplicationEvent(@NonNull  ContextRefreshedEvent event) {
        // инициализируем в AnnotatedVerticleMap поля AnnotatedVerticle.bean
        // на основании данных из кэша VerticleScopeConfigurator
        verticles.forEach((verticleName, annotatedVerticle) -> {
            if(cachedBeans.containsKey(annotatedVerticle.getBeanName()))
                annotatedVerticle.setBean((AbstractVerticle) cachedBeans.get(annotatedVerticle.getBeanName()));
        });
        // зарегистрируем фабрику
        vertx.registerVerticleFactory(factory);
        // деплоим кадую аннотированную вертикаль
        verticles.forEach((verticleName, annotatedVerticle) -> deployVerticle(annotatedVerticle));
    }

    /**
     * деплой аннотированной вертикали
     *
     * @param verticle аннотированная вертикаль
     */
    private void deployVerticle(AnnotatedVerticle verticle) {
        AbstractVerticle beanForCallBack;
        if(verticle.getBean() == null) {
            // если бина нет в кэше, значит он не аннотирован кастомным скоупом, но аннотирован как вертикаль
            DeploymentOptions options = verticle.getDeploymentOptions();
            if(options != null && options.isWorker())
                // если воркер не аннотирован нашим скоупом, то уведомим в логе про отсутствие гарантий для воркера
                logger.warn(String.format("Verticle '%s' have to be annotated with @Scope(\"verticle\")", verticle.getName()));
            // берём фабричный бин для колл-бека
            beanForCallBack = factory.getVerticleForBean(verticle.getBeanName());
        } else {
            // если бин есть в кэше, значит вертикаль аннотирована нашим кастомным скоупом;
            // в таких вслучаях ВСЕГДА создаём хендлер на колл-беки для бина из кэша, а деплоим бин,
            // запрошенный ещё раз у контекста Spring'а
            beanForCallBack = verticle.getBean();
        }
        // деплоим по имени вертикали
        String verticleName = factory.prefix() + ":" + verticle.getName();
        logger.debug(String.format("deploying verticle '%s' from bean '%s'...",
                verticleName, verticle.getBeanName()));
        // деплоим по имени вертикали, а соответствие между именем и конкретным бином - это ответственность фабрики
        if(verticle.getDeploymentOptions() == null) {
            // without options
            if(verticle.hasHandler()) {
                // with Handler
                vertx.deployVerticle(verticleName, verticle.getHandler(beanForCallBack));
            } else {
                // without Handler
                vertx.deployVerticle(verticleName);
            }
        } else {
            // with options
            if(verticle.hasHandler()) {
                // with Handler
                vertx.deployVerticle(verticleName, verticle.getDeploymentOptions(), verticle.getHandler(beanForCallBack));
            } else {
                // without Handler
                vertx.deployVerticle(verticleName, verticle.getDeploymentOptions());
            }
        }
    }
}
