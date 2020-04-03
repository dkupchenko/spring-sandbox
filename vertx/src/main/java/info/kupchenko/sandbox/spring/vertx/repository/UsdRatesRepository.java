package info.kupchenko.sandbox.spring.vertx.repository;

import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import info.kupchenko.summer.vertx.annotation.VerticleName;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс UsdRatesRepository представляет репозиторий-заглушку по генерации
 * некоего тренда изменений котировок валюты USD
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 28.03.2020
 * Last review on 28.03.2020
 */
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@VerticleName()
@SuppressWarnings("unused")
public class UsdRatesRepository extends AbstractVerticle implements RatesRepository {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(UsdRatesRepository.class);

    /**
     * rates starts from these values
     */
    private static final float USD_START_RATE = 77.7325f;

    /**
     * rates deviations for concrete currency
     */
    private static final float USD_DEVIATION = 05.0000f;

    /**
     * последняя запрошенная котировка
     */
    private Rate lastRate;

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
        lastRate = new Rate(Currency.USD, USD_START_RATE);
        vertx.setPeriodic(200, l -> vertx.eventBus().publish(eventBusId, getCurrentRate()));
        log.debug(String.format("periodic publishing is configured%s", context.isWorkerContext() ? " as worker" : ""));
    }

    /**
     * генерирует котировку валюты
     *
     * @return новаая котировка
     */
    @Override
    public Rate getCurrentRate() {
        float value = lastRate.getValue() + USD_DEVIATION * (2 * ThreadLocalRandom.current().nextFloat() - 1);
        return new Rate(Currency.USD, value);
    }
}
