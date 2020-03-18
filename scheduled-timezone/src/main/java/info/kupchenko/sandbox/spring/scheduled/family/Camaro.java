package info.kupchenko.sandbox.spring.scheduled.family;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The Camaro ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
@Component
@SuppressWarnings("unused")
public class Camaro implements Car {
    private static Log log = LogFactory.getLog(Camaro.class);
    private static final long CAR_DEFAULT_MAX_DELAY = 500;
    String model;
    long price = 50000;

    Camaro() {
        model = "Chevrolet Camaro 2020";
        log.debug(String.format("Creating %s", this));
    }

    // Car interface implementation
    @Override
    public String model() {
        return model;
    }

    @Override
    public long price() {
        return price;
    }

    @Override
    public void check(Essence sender) {
        System.out.println(String.format("'%s' checks '%s'", sender.name(), model));
    }

    @Override
    public void move(Essence sender) throws InterruptedException {
        log.info(String.format("%s takes a trip by %s", sender.name(), model));
        Thread.sleep(ThreadLocalRandom.current().nextLong(CAR_DEFAULT_MAX_DELAY));
    }

    @Override
    public String toString() {
        return String.format("Car '%s' costs %d", model, price);
    }
}
