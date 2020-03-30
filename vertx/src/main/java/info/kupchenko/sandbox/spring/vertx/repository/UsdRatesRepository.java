package info.kupchenko.sandbox.spring.vertx.repository;

import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;
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
public class UsdRatesRepository extends AbstractRatesRepository implements RatesRepository {
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
     * конструктор по умолчанию инициализирует суперкласс экземпляром используемой валюты
     */
    public UsdRatesRepository() {
        super(Currency.USD);
        lastRate = new Rate(currency, USD_START_RATE);
    }

    /**
     * возвращает текущую котировку валюты
     *
     * @return текущая котировка
     */
    @Override
    public Rate getCurrentRate() {
        float value = lastRate.getValue() + USD_DEVIATION * (2 * ThreadLocalRandom.current().nextFloat() - 1);
        return new Rate(currency, value);
    }
}
