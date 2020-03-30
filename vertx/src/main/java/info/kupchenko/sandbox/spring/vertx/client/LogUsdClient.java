package info.kupchenko.sandbox.spring.vertx.client;

import info.kupchenko.sandbox.spring.vertx.entities.Rate;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Класс SimpleClient является имплементацией вертикали клиента-потребителя котировок валют с выводом результатов в лог
 *
 * @author by Dmitry Kupchenko
 * @version 2.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
@Component
@SuppressWarnings("unused")
public class LogUsdClient extends AbstractVerticle implements Client {
    /**
     * for logging using default logging system implementation
     */
    private static final Log log = LogFactory.getLog(LogUsdClient.class);

    /**
     * Vertx instance
     */
    private final Vertx vertx;

    /**
     * EventBus Rates address
     */
    private final String eventBusId;

    /**
     * подписчик на котировки, нужен для unregister()
     */
    private MessageConsumer<Rate> consumer;

    /**
     * Инжекция зависимостей через конструктор
     * @param vertx бин vertx
     * @param eventBusId адрес котировок
     */
    public LogUsdClient(Vertx vertx, @Value("${custom.event-bus.rate-id}") String eventBusId) {
        this.vertx = vertx;
        this.eventBusId = eventBusId;
    }

    /**
     * Старт вертикали просле деплоя
     */
    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();
        consumer = eventBus.consumer(eventBusId, message -> processCurrentRates(message.body()));
        log.debug("consumer is registered");
    }

    /**
     * Стоп вертикали при завершении приложения
     */
    @Override
    public void stop() {
        consumer.unregister();
        log.debug("consumer is unregistered");
    }

    @Override
    public void processCurrentRates(Rate rate) {
        log.info(rate);
    }
}
