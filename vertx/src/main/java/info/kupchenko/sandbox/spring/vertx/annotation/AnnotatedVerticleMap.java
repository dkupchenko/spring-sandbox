package info.kupchenko.sandbox.spring.vertx.annotation;

import java.util.Map;

/**
 * Интерфейс AnnotatedVerticleMap является абстракцией хранилища пар
 * [имя аннотированной вертикали, экземпляр аннотированной вертикаль] = [String, AnnotatedVerticle]
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 01.04.2020
 * Last review on 01.04.2020
 */
interface AnnotatedVerticleMap extends Map<String, AnnotatedVerticle> {
}
