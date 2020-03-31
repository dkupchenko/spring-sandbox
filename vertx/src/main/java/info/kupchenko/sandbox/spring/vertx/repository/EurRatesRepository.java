package info.kupchenko.sandbox.spring.vertx.repository;

import info.kupchenko.sandbox.spring.vertx.annotation.DeployOptions;
import info.kupchenko.sandbox.spring.vertx.annotation.OnDeployError;
import info.kupchenko.sandbox.spring.vertx.annotation.OnDeploySuccess;
import info.kupchenko.sandbox.spring.vertx.annotation.Verticle;
import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс EurRatesRepository представляет репозиторий-заглушку по генерации
 * некоего тренда изменений котировок валюты EUR
 *
 * @author by Dmitry Kupchenko
 * @version 3.0
 * @since 3.0
 * Created on 31.03.2020
 * Last review on 31.03.2020
 */
@Component
@Verticle(worker = true)
@SuppressWarnings("unused")
public class EurRatesRepository extends AbstractVerticle implements RatesRepository {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(EurRatesRepository.class);

    /**
     * rates starts from these values
     */
    private static final float START_RATE = 85.1234f;

    /**
     * rates deviations for concrete currency
     */
    private static final float DEVIATION = 03.0000f;

    /**
     * последняя запрошенная котировка
     */
    private Rate lastRate;

    @DeployOptions
    private DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(true);

    /**
     * EventBus Rates address
     */
    @Value("${custom.event-bus.rate-id}")
    private String eventBusId;

    /**
     * инициализация вертикали после деплоя
     */
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start();
        lastRate = new Rate(Currency.EUR, START_RATE);
        vertx.setPeriodic(1500, l -> vertx.eventBus().publish(eventBusId, getCurrentRate()));
        log.debug(String.format("periodic publishing is configured%s", context.isWorkerContext() ? " as worker" : ""));
    }

    /**
     * зенерирует новую котировку валюты со случайной задержкой
     *
     * @return новая котировка
     */
    @Override
    public Rate getCurrentRate() {
        float value = lastRate.getValue() + DEVIATION * (2 * ThreadLocalRandom.current().nextFloat() - 1);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(2000));
        } catch (InterruptedException e) {
            log.error("interrupted");
        }
        return new Rate(Currency.EUR, value);
    }

    /**
     * вызывается при успешном деплое вертикали
     */
    @OnDeploySuccess
    public void deploySuccess(String id) {
        log.info(String.format("Deploy success with id [%s]", id));
    }

    /**
     * вызывается при ошибке деплоя вертикали
     */
    @OnDeployError
    public void deployError() {
        log.error("Deploy failure");
    }
}
