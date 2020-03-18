package info.kupchenko.sandbox.spring.scheduled.family;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The Cat ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
@Component
@SuppressWarnings("unused")
public class Cat implements Pet {
    private static Log log = LogFactory.getLog(Cat.class);

    private static final long CAT_DEFAULT_MAX_DELAY = 1000;
    private static final String name = "Mia";
    volatile EssenceState state = EssenceState.IDLE;
    Thread restThread;
    Thread sleepThread;

    public Cat() {
        log.debug(String.format ("Creating %s" ,this));
    }

    // Essence interface implementation
    @Override
    public String name() {
        return name;
    }

    @Override
    public EssenceState state() {
        return state;
    }

    @Scheduled(fixedRate=2000)
    @Override
    public void rest() {
        if(state != EssenceState.IDLE) {
            log.warn(String.format("%s can't take a rest", name));
            return;
        }
        try {
            restThread = Thread.currentThread();
            state = EssenceState.REST;
            log.info(String.format("%s takes a rest", name));
            Thread.sleep(ThreadLocalRandom.current().nextLong(CAT_DEFAULT_MAX_DELAY));
        } catch (InterruptedException e) {
            log.error(String.format("%s rest is interrupted", name));
        } finally {
            restThread = null;
            state = EssenceState.IDLE;
        }
    }

    @Scheduled(fixedDelay=3000)
    @Override
    public void sleep() {
        if(state != EssenceState.IDLE) {
            log.warn(String.format("%s can't sleep", name));
            return;
        }
        try {
            sleepThread = Thread.currentThread();
            state = EssenceState.SLEEP;
            log.info(String.format("%s sleeps", name));
            Thread.sleep(ThreadLocalRandom.current().nextLong(CAT_DEFAULT_MAX_DELAY));
        } catch (InterruptedException e) {
            log.error(String.format("%s sleep is interrupted", name));
        } finally {
            sleepThread = null;
            state = EssenceState.IDLE;
        }
    }

    // Pet interface implementation
    @Override
    public void stroke(Essence sender) {
        log.info(String.format("%s strokes the %s", sender.name(), name));
    }

    @Override
    public void play(Essence sender) throws InterruptedException {
        switch (state) {
            case REST:
                if(restThread != null) restThread.interrupt();
                break;
            case SLEEP:
                if(sleepThread != null) sleepThread.interrupt();
                break;
            case BUSY:
                log.error(String.format("%s is busy", name));
                return;
        }
        try {
            state = EssenceState.BUSY;
            log.info(String.format("%s is playing with %s", sender.name(), name));
            Thread.sleep(ThreadLocalRandom.current().nextLong(CAT_DEFAULT_MAX_DELAY));
        } catch (InterruptedException e) {
            log.error(String.format("%s is interrupted", name));
            throw new InterruptedException();
        } finally {
            state = EssenceState.IDLE;
        }
    }

    @Override
    public String toString() {
        return String.format("%s={name='%s', state=%s}", this.getClass().getSimpleName(), name, state);
    }
}
