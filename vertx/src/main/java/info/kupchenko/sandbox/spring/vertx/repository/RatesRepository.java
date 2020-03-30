package info.kupchenko.sandbox.spring.vertx.repository;

import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;

/**
 * Интерфейс RatesSource представляет бин репозитория валюты.
 * <b>ВАЖНО: Каждый бин обслуживает одну валюту.</b> Если в контексте присутствует несколько бинов, оперирующих
 * с одной валютой, то необходимо использовать аннотацию @Order для описания приоритета бинов. В этом случае
 * использоваться будет бин с наивысшим приоритетом (<b>наименьшим порядком</b>).
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 29.03.2020
 * Last review on 29.03.2020
 */
public interface RatesRepository {
    /**
     * возвращает валюту, с которой работает данный репозиторий
     *
     * @return валюта
     */
    Currency getCurrency();

    /**
     * возвращает текущую котировку валюты
     *
     * @return текущая котировка
     */
    Rate getCurrentRate();
}
