package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The VerticleHelper ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
class VerticleHelper {

    private final String beanName;
    private final String beanClassName;
    private AbstractVerticle bean;
    private final DeploymentOptions deploymentOptions;
    private final Method onDeploySuccess;
    private final Method onDeployError;
    private final DeploymentResultHandler handler;

    VerticleHelper(Object bean, String beanName) {
        this.beanName = beanName;
        Class<?> beanClass = bean.getClass();
        beanClassName = bean.getClass().getCanonicalName();
        // searching for DeploymentOptions
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
        deploymentOptions = options;
        // searching for deploy callbacks
        Method onDeploySuccessTemp = null;
        Method onDeployErrorTemp = null;
        for(Method method: beanClass.getDeclaredMethods()) {
            if(method.isAnnotationPresent(OnDeploySuccess.class) && onDeploySuccessTemp == null)
                onDeploySuccessTemp = checkDeploySuccessMethod(method);
            if(method.isAnnotationPresent(OnDeployError.class) && onDeployErrorTemp == null)
                onDeployErrorTemp = checkDeployErrorMethod(method);
        }
        onDeploySuccess = onDeploySuccessTemp;
        onDeployError = onDeployErrorTemp;
        // handler
        handler = (onDeploySuccess != null || onDeployError != null) ? new DeploymentResultHandler(this) : null;
    }

    protected Method checkDeploySuccessMethod(Method method) {
        // more than one parameter
        if(method.getParameters().length > 1) return null;
        // without parameters
        if(method.getParameters().length == 0) return method;
        // only one parameter
        Class<?>[] parameterTypes = method.getParameterTypes();
        return (parameterTypes[0] == String.class) ? method : null;
    }

    protected Method checkDeployErrorMethod(Method method) {
        return (method.getParameters().length == 0) ? method : null;
    }

    public void setBean(AbstractVerticle bean) {
        this.bean = bean;
    }

    @SuppressWarnings("unused")
    public String getBeanName() {
        return beanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public AbstractVerticle getBean() {
        return bean;
    }

    public DeploymentOptions getDeploymentOptions() {
        return deploymentOptions;
    }

    public Method getOnDeploySuccess() {
        return onDeploySuccess;
    }

    public Method getOnDeployError() {
        return onDeployError;
    }

    public DeploymentResultHandler getHandler() {
        return handler;
    }
}
