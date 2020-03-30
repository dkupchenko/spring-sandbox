package info.kupchenko.sandbox.spring.vertx.service;

import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import info.kupchenko.sandbox.spring.vertx.repository.RatesRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Класс RatesServiceImpl имплементирует бин сервиса валют, предоставляя текущие котировки валют
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 29.03.2020
 * Last review on 29.03.2020
 */
@Service
@SuppressWarnings("unused")
public class RatesServiceImpl implements RatesService {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(RatesServiceImpl.class);

    /**
     * HashMap for storing Currency -> RatesRepository pairs
     */
    private final Map<Currency, RatesRepository> repos = new HashMap<>();

    /**
     * Наполняет хэш-мапу в соответствии с имеющимися бинами RatesRepository.
     * Не принимает в расчёт репозитарии, работающие с null-валютами.
     * В случае, если несколько репозиториев обслуживают одну и ту же валюту, берётся в рассмотрение первый по списку
     *
     * @param repositories список бинов RatesRepository (Spring constructor injection)
     */
    public RatesServiceImpl(List<RatesRepository> repositories) {
        for (RatesRepository repo: repositories) {
            if(repo.getCurrency() == null) {
                // Что-то пошло не так
                String msg = String.format("There is repository witch works with null currency %s", repo.getClass().getSimpleName());
                log.error(msg);
                throw new IllegalArgumentException(msg);
            }
            Currency currency = repo.getCurrency();
            if(!repos.containsKey(currency))
                repos.put(currency, repo);
        }
        log.debug(String.format("RatesServiceImpl created with %d repo(s)", repositories.size()));
    }

    /**
     * проверка валюты на наличие в списке обслуживаемых
     * @param currency проверяемая валюта
     */
    private void checkCurrency(Currency currency) {
        if(repos.containsKey(currency)) return;
        // Что-то пошло не так
        String msg = String.format("There is no repository for currency %s", currency);
        log.error(msg);
        throw new IllegalArgumentException(msg);
    }

    /**
     * возвращает набор обслуживаемых валют
     *
     * @return набор обслуживаемых валют
     */
    @Override
    public Set<Currency> getCurrencies() {
        return repos.keySet();
    }

    /**
     * возвращает текущую котировку валюты
     *
     * @param currency валюта
     * @return теукщая котирвка
     */
    @Override
    public Rate getCurrentRate(Currency currency) {
        log.debug(String.format("getCurrent(%s)", currency));
        checkCurrency(currency);
        return repos.get(currency).getCurrentRate();
    }
}
