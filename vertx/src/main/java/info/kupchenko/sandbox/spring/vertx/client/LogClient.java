package info.kupchenko.sandbox.spring.vertx.client;

import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Класс LogClient является имплементацией вертикали клиента-потребителя котировок валют с выводом результатов в лог
 *
 * @author by Dmitry Kupchenko
 * @version 3.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@SuppressWarnings("unused")
public class LogClient extends AbstractVerticle implements Client {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(LogClient.class);

    /**
     * EventBus Rates address
     */
    @Value("${custom.event-bus.rate-id}")
    private String eventBusId;

    /**
     * Старт вертикали просле деплоя
     */
    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus().<Rate>consumer(eventBusId, message -> processCurrentRates(message.body()));
        log.debug(String.format("consumer is registered%s", context.isWorkerContext() ? " as worker" : ""));
    }

    /**
     * логгирование получения котировки
     * @param rate котировка
     */
    @Override
    public void processCurrentRates(Rate rate) {
        log.info(rate);
    }
}
