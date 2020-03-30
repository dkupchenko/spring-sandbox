package info.kupchenko.sandbox.spring.vertx.client;


import info.kupchenko.sandbox.spring.vertx.entities.Currency;
import info.kupchenko.sandbox.spring.vertx.service.RatesService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Класс SimpleClient является имплементацией бина клиента-потребителя котировок валют с выводом результатов в лог
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
@Component
public class LogUsdClient implements Client {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(LogUsdClient.class);

    private final static Currency currency = Currency.USD;

    private final RatesService service;


    public LogUsdClient(RatesService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 200, initialDelay = 200)
    @Override
    public void processCurrentRates() {
        log.info(service.getCurrentRate(currency));
    }
}
