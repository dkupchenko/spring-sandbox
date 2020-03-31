package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

/**
 * The VertXDeployerContextListener ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
public class VertXDeployerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    VertXHandlerBeanPostProcessor vertXHandlerBeanPostProcessor;

    @Autowired
    Vertx vertx;

    @Override
    public void onApplicationEvent(@NonNull  ContextRefreshedEvent event) {
        vertXHandlerBeanPostProcessor.getVerticles().forEach(
                (beanName, verticle) -> deployVerticle(verticle)
        );
    }

    void deployVerticle(VerticleHelper verticle) {
        if(verticle.getDeploymentOptions() == null) {
            // without options
            if(verticle.getHandler() != null) {
                // with Handler
                vertx.deployVerticle(verticle.getBean(), verticle.getHandler());
            } else {
                // without Handler
                vertx.deployVerticle(verticle.getBean());
            }
        } else {
            // with options
            if(verticle.getHandler() != null) {
                // with Handler
                vertx.deployVerticle(verticle.getBean(), verticle.getDeploymentOptions(), verticle.getHandler());
            } else {
                // without Handler
                vertx.deployVerticle(verticle.getBean(), verticle.getDeploymentOptions());
            }
        }

    }
}
