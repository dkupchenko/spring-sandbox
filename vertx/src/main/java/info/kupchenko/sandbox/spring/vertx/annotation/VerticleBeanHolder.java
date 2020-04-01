package info.kupchenko.sandbox.spring.vertx.annotation;

import io.vertx.core.AbstractVerticle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Класс VerticleBeanHolder необходим для инициализации наследников AbstractVerticle Spring'ом вне зависимости от их
 * scope
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 01.04.2020
 * Last review on 01.04.2020
 */
@Component
@SuppressWarnings("unused")
class VerticleBeanHolder {
    /**
     * for logging
     */
    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    List<AbstractVerticle> verticles;

    public List<AbstractVerticle> getVerticles() {
        return verticles;
    }

    @PostConstruct
    void init() {
        log.debug(String.format("verticles count = %d", verticles.size()));
    }
}
