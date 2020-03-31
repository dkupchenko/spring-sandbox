package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The DeploymentResultHandler ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
public class DeploymentResultHandler implements Handler<AsyncResult<String>> {

    private final Log logger = LogFactory.getLog(getClass());

    private VerticleHelper verticle;

    public DeploymentResultHandler(VerticleHelper verticle) {
        this.verticle = verticle;
    }

    @Override
    public void handle(AsyncResult<String> result) {
        // if success
        Method onSuccess = verticle.getOnDeploySuccess();
        if(result.succeeded() && onSuccess != null) {
            // invoke onSuccess callback
            onSuccess.setAccessible(true);
            Class<?>[] params = onSuccess.getParameterTypes();
            if (params.length == 0) {
                // if invoke without params
                try {
                    onSuccess.invoke(verticle.getBean());
                    return;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                // invoke with String param
                try {
                    onSuccess.invoke(verticle.getBean(), result.result());
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
            onError.setAccessible(true);
            try {
                onError.invoke(verticle.getBean());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
