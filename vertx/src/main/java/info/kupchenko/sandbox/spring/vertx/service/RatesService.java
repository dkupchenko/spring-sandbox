package info.kupchenko.sandbox.spring.vertx.service;

import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;

import java.util.Set;

/**
 * Интерфейс RatesService описывает бин сервиса валют, который может предоставить текущие котировки по валютам
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 29.03.2020
 * Last review on 29.03.2020
 */
public interface RatesService {
    /**
     * возвращает набор обслуживаемых валют
     *
     * @return набор обслуживаемых валют
     */
    Set<Currency> getCurrencies();

    /**
     * возвращает текущую котировку валюты
     *
     * @param currency валюта
     * @return теукщая котирвка
     */
    Rate getCurrentRate(Currency currency);
}
