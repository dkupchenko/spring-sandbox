package info.kupchenko.sandbox.spring.vertx.repository;

import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;

/**
 * Класс AbstractRatesRepository имплементирует интерфейс RatesRepository, оставляя наследникам задачу загрузки
 * текущих курсов/котировок валюты и первоначальной инициализацию последней запрошенной котировкаи.
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 29.03.2020
 * Last review on 29.03.2020
 */
public abstract class AbstractRatesRepository implements RatesRepository {
    /**
     * обслуживаемая валюта
     */
    protected final Currency currency;

    /**
     * Конструктор по умолчанию инициализирует lastRate для обеспечения контракта RatesRepository
     */
    protected AbstractRatesRepository(Currency currency) {
        this.currency = currency;
    }

    /**
     * возвращает валюту, с которой работает данный репозиторий

     * @return валюта
     */
    @Override
    public final Currency getCurrency() {
        return currency;
    }

    /**
     * возвращает текущую котировку валюты
     *
     * @return текущая котировка
     */
    @Override
    public abstract Rate getCurrentRate();
}
