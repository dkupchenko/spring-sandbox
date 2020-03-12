package info.kupchenko.sandbox.spring.circling.family;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The DeLorean ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
public class DeLorean implements Car {
    private static final long CAR_DEFAULT_MAX_DELAY = 500;
    String model;
    long price = 50000;

    DeLorean() {
        model = "DeLorean DMC-12";
        System.out.println(this);
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
        System.out.println(String.format("[T-%d] %s takes a trip by %s", Thread.currentThread().getId(), sender.name(), model));
        Thread.sleep(ThreadLocalRandom.current().nextLong(CAR_DEFAULT_MAX_DELAY));
    }

    @Override
    public String toString() {
        return String.format("Car '%s' costs %d", model, price);
    }
}
