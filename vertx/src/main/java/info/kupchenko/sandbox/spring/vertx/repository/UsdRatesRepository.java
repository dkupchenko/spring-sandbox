package info.kupchenko.sandbox.spring.vertx.repository;

import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
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
     * бин Vertx
     */
    private final Vertx vertx;

    /**
     * EventBus Rates address
     */
    private final String eventBusId;

    /**
     * последняя запрошенная котировка
     */
    private Rate lastRate;

    /**
     * конструктор по умолчанию
     */
    public UsdRatesRepository(Vertx vertx, @Value("${custom.event-bus.rate-id}") String eventBusId) {
        this.vertx = vertx;
        this.eventBusId = eventBusId;
        lastRate = new Rate(Currency.USD, USD_START_RATE);
    }

    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();
        vertx.setPeriodic(200, l -> eventBus.publish(eventBusId, getCurrentRate()));
        log.debug("periodic publishing is configured");
    }

    /**
     * возвращает текущую котировку валюты
     *
     * @return текущая котировка
     */
    @Override
    public Rate getCurrentRate() {
        float value = lastRate.getValue() + USD_DEVIATION * (2 * ThreadLocalRandom.current().nextFloat() - 1);
        return new Rate(Currency.USD, value);
    }
}
