package info.kupchenko.sandbox.spring.circling.family;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The Dog ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
@Component
@SuppressWarnings("unused")
public class Dog implements Pet {
    private static Log log = LogFactory.getLog(Dog.class);
    private static final long DOG_DEFAULT_MAX_DELAY = 500;

    String name;

    public Dog() {
        name = "Rocky";
        log.debug(this);
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
        log.info(String.format("%s strokes %s", sender.name(), name));
    }

    @Override
    public void play(Essence sender) throws InterruptedException {
        log.info(String.format("%s is playing with %s", sender.name(), name));
        Thread.sleep(ThreadLocalRandom.current().nextLong(DOG_DEFAULT_MAX_DELAY));
    }

    @Override
    public String toString() {
        return String.format("%s with name '%s'", this.getClass().getSimpleName(), name);
    }
}
