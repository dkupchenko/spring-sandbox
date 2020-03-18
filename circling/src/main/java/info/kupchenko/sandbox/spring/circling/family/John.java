package info.kupchenko.sandbox.spring.circling.family;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The John ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
@Service
@SuppressWarnings("unused")
public class John implements Husband {
    private static Log log = LogFactory.getLog(John.class);
    private static final long JOHN_DEFAULT_MAX_DELAY = 500;
    private static final long MAX_AMOUNT = 1000L;

    Thread foregroundThread;
    String name;

    Pet pet;
    @Autowired
    Car car;
    Wife wife;

    public John(Pet pet) {
        this.pet = pet;
        name = "John";
        log.debug(this);
        pet.stroke(this);
        log.debug(String.format("%s looks on car: %s", name, car));
        log.debug(String.format("%s looks on wife: %s", name, wife));
    }

    // LifeCycle interface implementation
    @Async
    @Override
    public void start() {
        try {
            if(isRunning()) return;
            log.debug(String.format("%s IS STARTED", name));
            log.info(String.format("%s: Hi, %s...", name, wife.name()));
            foregroundThread = Thread.currentThread();
            while(!Thread.interrupted()) {
                log.info(String.format("%s wants to take rest", name));
                rest();
                Thread.sleep(ThreadLocalRandom.current().nextLong(JOHN_DEFAULT_MAX_DELAY));
            }
        } catch (InterruptedException e) {
            log.debug(String.format("%s IS INTERRUPTED", name));
        }
    }

    @Override
    public void stop() {
        if(!isRunning()) return;
        log.debug(String.format("%s IS STOPPED", name));
        foregroundThread.interrupt();
        foregroundThread = null;
    }

    @Override
    public boolean isRunning() {
        return (foregroundThread != null);
    }

    // Essence interface implementation
    @Override
    public String name() {
        return name;
    }

    @Async
    @Override
    public void rest() throws InterruptedException {
        log.info(String.format("%s takes a rest", name));
        Thread.sleep(ThreadLocalRandom.current().nextLong(JOHN_DEFAULT_MAX_DELAY));
        pet.play(this);
        car.move(this);
        wife.smile();
    }

    // Husband interface implementation
    @Async
    @Override
    public Future<Long> getMoney(Essence sender) throws InterruptedException {
        long amount = ThreadLocalRandom.current().nextLong(MAX_AMOUNT);
        log.info(String.format("%s gives %d$ to %s", name, amount, sender.name()));
        Thread.sleep(ThreadLocalRandom.current().nextLong(JOHN_DEFAULT_MAX_DELAY));
        return CompletableFuture.completedFuture(amount);
    }

    @Override
    public void setWife(Wife wife) {
        this.wife = wife;
    }

    @Override
    public String toString() {
        return String.format("%s with name '%s'", this.getClass().getSimpleName(), name);
    }
}
