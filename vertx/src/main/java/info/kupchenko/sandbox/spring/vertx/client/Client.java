package info.kupchenko.sandbox.spring.vertx.client;


/**
 * Интерфейс Client описывает бин клиента-потребителя котировок валют
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
public interface Client {
    void processCurrentRates();
}
