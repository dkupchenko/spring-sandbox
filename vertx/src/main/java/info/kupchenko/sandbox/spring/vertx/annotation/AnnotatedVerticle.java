package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

import java.lang.reflect.Method;

/**
 * Immutable класс VerticleHelper хранит данные об аннотированных вертикалях
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 31.03.2020
 * Last review on 01.04.2020
 */
class AnnotatedVerticle {
    /**
     * имя вертикали
     */
    private final String name;
    /**
     * имя соответствующего бина в Spring
     */
    private final String beanName;
    /**
     * опции для деплоя
     */
    private final DeploymentOptions deploymentOptions;
    /**
     * метод колл-бека при удачном деплое
     */
    private final Method onDeploySuccess;
    /**
     * метод колл-бека при ошибочном деплое
     */
    private final Method onDeployError;

    private AbstractVerticle bean = null;

    /**
     * конструктор
     *
     * @param bean бин
     * @param beanName имя бина
     */
    AnnotatedVerticle(AbstractVerticle bean, String beanName) {
        this.beanName = beanName;
        Class<?> beanClass = bean.getClass();
        // инициализируем поле name
        String tmpVerticleName = beanName;
        if(beanClass.isAnnotationPresent(Verticle.class)) {
            Verticle annotation = beanClass.getAnnotation(Verticle.class);
            if(annotation != null && !annotation.name().equals(""))
                tmpVerticleName = annotation.name();
        }
        this.name = tmpVerticleName;
        // searching for DeploymentOptions
        this.deploymentOptions = AnnotatedVerticleHelper.getDeploymentOptions(bean);
        // searching for deploy callbacks
        Method onDeploySuccessTemp = null;
        Method onDeployErrorTemp = null;
        for(Method method: beanClass.getDeclaredMethods()) {
            if(method.isAnnotationPresent(OnDeploySuccess.class) && onDeploySuccessTemp == null)
                onDeploySuccessTemp = checkDeploySuccessMethod(method);
            if(method.isAnnotationPresent(OnDeployError.class) && onDeployErrorTemp == null)
                onDeployErrorTemp = checkDeployErrorMethod(method);
        }
        this.onDeploySuccess = onDeploySuccessTemp;
        this.onDeployError = onDeployErrorTemp;
    }

    /**
     * проверка, возможен для вызов аннотированного метода без параметров или с единственным параметром типа String
     *
     * @param method метод для анализа
     * @return метод в случае, если его можно вызвать как колл-бек (или null в противном случае)
     */
    protected Method checkDeploySuccessMethod(Method method) {
        // more than one parameter
        if(method.getParameters().length > 1) return null;
        // without parameters
        if(method.getParameters().length == 0) return method;
        // only one parameter
        Class<?>[] parameterTypes = method.getParameterTypes();
        return (parameterTypes[0] == String.class) ? method : null;
    }

    /**
     * проверка, возможен для вызов аннотированного метода без параметров
     *
     * @param method метод для анализа
     * @return метод в случае, если его можно вызвать как колл-бек (или null в противном случае)
     */
    protected Method checkDeployErrorMethod(Method method) {
        return (method.getParameters().length == 0) ? method : null;
    }

    /**
     * возвращает имя вертикали; в случае, когда вертикаль аннотирована без имени (name = ""),
     * используется имя бина
     *
     * @return имя вертикали
     */
    public String getName() {
        return name;
    }

    /**
     * возвращает имя бина
     *
     * @return имя бина
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * возвращает опции для деплоя
     *
     * @return опции для деплоя
     */
    public DeploymentOptions getDeploymentOptions() {
        return deploymentOptions;
    }

    /**
     * возвращает метод колл-бека при успешном деплое
     *
     * @return метод колл-бека при успешном деплое
     */
    public Method getOnDeploySuccess() {
        return onDeploySuccess;
    }

    /**
     * возвращает метод колл-бека при ошибочном деплое
     *
     * @return метод колл-бека при ошибочном деплое
     */
    public Method getOnDeployError() {
        return onDeployError;
    }

    /**
     * возвращает true, если вертикаль содержит методы для колл-бека статуса деплоя
     * @return true, если вертикаль содержит методы для колл-бека статуса деплоя (false в противном случае)
     */
    public boolean hasHandler() {
        return onDeploySuccess != null || onDeployError != null;
    }

    /**
     * возвращает хендлер коллбека деплоя, каждый раз при вызове создаёт новый экземпляр
     *
     * @return хендлер коллбека деплоя (или null, если хендлер не может быть создан)
     */
    public DeploymentResultHandler getHandler(AbstractVerticle bean) {
        return (hasHandler() && bean != null) ?
                new DeploymentResultHandler(this, bean) : null;
    }

    /**
     * возвращает экземпляр бина
     *
     * @return бин вертикали
     */
    public AbstractVerticle getBean() {
        return bean;
    }

    /**
     * изменяет значение значение бина
     *
     * @param bean бин вертикали
     */
    public void setBean(AbstractVerticle bean) {
        this.bean = bean;
    }
}
