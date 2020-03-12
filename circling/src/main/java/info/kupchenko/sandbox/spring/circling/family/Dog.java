package info.kupchenko.sandbox.spring.circling.family;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The Dog ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
public class Dog implements Pet {
    private static final long DOG_DEFAULT_MAX_DELAY = 500;

    String name;

    public Dog() {
        name = "Rocky";
        System.out.println(this);
    }

    // LifeCycle interface implementation
    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public boolean isRunning() {
        return false;
    }

    // Essence interface implementation
    @Override
    public String name() {
        return name;
    }

    @Override
    public void rest() {}

    // Pet interface implementation
    @Override
    public void stroke(Essence sender){
        System.out.println(String.format("'%s' strokes the '%s'", sender.name(), name));
    }

    @Override
    public void play(Essence sender) throws InterruptedException {
        System.out.println(String.format("[T-%d] %s is playing with %s", Thread.currentThread().getId(), sender.name(), name));
        Thread.sleep(ThreadLocalRandom.current().nextLong(DOG_DEFAULT_MAX_DELAY));
    }

    @Override
    public String toString() {
        return String.format("%s with name '%s'", this.getClass().getSimpleName(), name);
    }
}
