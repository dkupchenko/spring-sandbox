package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Класс DeploymentResultHandler является обёрткой колл-бека для Vertx, указывающим на методы вертикалей,
 * ответственные за реагирование на успех/ошибку деплоя
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
public class DeploymentResultHandler implements Handler<AsyncResult<String>> {
    /**
     * для логирования
     */
    private final Log logger = LogFactory.getLog(getClass());
    /**
     * экземпляр аннотированной вертикали
     */
    private final AnnotatedVerticle verticle;
    /**
     * бин вертикали
     */
    private final AbstractVerticle bean;

    /**
     * конструктор для инжекции
     *
     * @param verticle экземпляр аннотированной вертикали
     * @param bean бин вертикали
     */
    public DeploymentResultHandler(AnnotatedVerticle verticle, AbstractVerticle bean) {
        this.verticle = verticle;
        this.bean = bean;
    }

    /**
     * хендлер-обёртка, вызывающий методы верикали при успешном/ошибочном деплое вертикали
     *
     * @param result асинхронный результат деплоя
     */
    @Override
    public void handle(AsyncResult<String> result) {
        // if success
        Method onSuccess = verticle.getOnDeploySuccess();
        if(result.succeeded() && onSuccess != null) {
            // invoke onSuccess callback
            logger.debug(String.format("success result for bean %s [%s]", verticle.getBeanName(), result.result()));
            onSuccess.setAccessible(true);
            Class<?>[] params = onSuccess.getParameterTypes();
            if (params.length == 0) {
                // if invoke without params
                try {
                    onSuccess.invoke(bean);
                    return;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                // invoke with String param
                try {
                    onSuccess.invoke(bean, result.result());
                    return;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        // if error
        Method onError = verticle.getOnDeployError();
        if(result.failed() && onError != null) {
            // invoke onError callback
            logger.debug(String.format("error result for bean %s [%s]", verticle.getBeanName(), result.result()));
            onError.setAccessible(true);
            try {
                onError.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
