package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.spi.VerticleFactory;

/**
 * ИНтерфейс SpringVerticleFactory явяется описанием сущности адаптера - фабрики вертикалей.
 * Он имплементирует фабрику вертикалей Vertx, а так же осуществляем поиск бина по названию вертикали
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 01.04.2020
 * Last review on 01.04.2020
 */
interface SpringVerticleFactory extends VerticleFactory {
    AbstractVerticle getVerticleForBean(String beanName) throws IllegalArgumentException;
}
