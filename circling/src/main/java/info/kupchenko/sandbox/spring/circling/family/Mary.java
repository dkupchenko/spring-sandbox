package info.kupchenko.sandbox.spring.circling.family;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Mary ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
@Service
@SuppressWarnings("unused")
public class Mary implements Wife {
    private static Log log = LogFactory.getLog(Mary.class);
    private static final long MARY_DEFAULT_MAX_DELAY = 500;
    String name;
    Thread foregroundThread;
    Pet pet;
    Husband husband;

    Mary(Pet pet) {
        this.pet = pet;
        name = "Mary";
        log.debug(this);
        pet.stroke(this);
        log.debug(String.format("Mary looks on husband: %s", husband));
    }

    // LifeCycle interface implementation
    @Async
    @Override
    public void start() {
        try {
            if(isRunning()) return;
            log.debug(String.format("%s IS STARTED", name));
            log.info(String.format("%s: Hi, %s...", name, husband.name()));
            foregroundThread = Thread.currentThread();
            while (!Thread.interrupted()) {
                log.info(String.format("%s wants to take rest", name));
                rest();
                Thread.sleep(ThreadLocalRandom.current().nextLong(MARY_DEFAULT_MAX_DELAY));
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
        try {
            log.info(String.format("%s takes a rest", name));
            Thread.sleep(ThreadLocalRandom.current().nextLong(MARY_DEFAULT_MAX_DELAY));
            log.info(String.format("%s: %s, give me please some money...", name, husband.name()));
            long amount = husband.getMoney(this).get();
            log.info(String.format("%s: Hmm, only %d$ ...", name, amount));
        } catch (ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Wife interface implementation
    @Async
    @Override
    public void smile() throws InterruptedException {
        log.info(String.format("%s smiles", name));
        Thread.sleep(ThreadLocalRandom.current().nextLong(MARY_DEFAULT_MAX_DELAY));
    }

    @Override
    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    @Override
    public String toString() {
        return String.format("Wife with name '%s'", name);
    }
}
