package info.kupchenko.sandbox.spring.vertx.repository;

import info.kupchenko.sandbox.spring.vertx.annotation.OnDeployError;
import info.kupchenko.sandbox.spring.vertx.annotation.OnDeploySuccess;
import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Класс RubRatesRepository представляет репозиторий-заглушку по генерации котировок валюты RUB;
 * данный репозиторий является примером стандартного деплоя (смешанный режим деплоя вертикалей)
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 28.03.2020
 * Last review on 28.03.2020
 */
@Repository
@SuppressWarnings("unused")
public class RubRatesRepository extends AbstractVerticle implements RatesRepository {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(RubRatesRepository.class);

    /**
     * EventBus Rates address
     */
    @Value("${custom.event-bus.rate-id}")
    private String eventBusId;

    /**
     * инициализация вертикали после деплоя
     */
    @Override
    public void start() throws Exception {
        super.start();
        vertx.setPeriodic(5000, l -> vertx.eventBus().publish(eventBusId, getCurrentRate()));
        log.debug(String.format("periodic publishing is configured%s", context.isWorkerContext() ? " as worker" : ""));
    }

    /**
     * генерирует котировку валюты
     *
     * @return новаая котировка
     */
    @Override
    public Rate getCurrentRate() {
        return new Rate(Currency.RUB, 1.0000f);
    }

    /**
     * вызывается при успешном деплое вертикали
     */
    public void deploySuccess(String id) {
        log.info(String.format("Deploy success with id [%s]", id));
    }

    /**
     * вызывается при ошибке деплоя вертикали
     */
    public void deployError() {
        log.error("Deploy failure");
    }

}
