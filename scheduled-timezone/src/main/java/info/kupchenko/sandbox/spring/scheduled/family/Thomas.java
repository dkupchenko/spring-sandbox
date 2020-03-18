package info.kupchenko.sandbox.spring.scheduled.family;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The Thomas ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
@Service
@SuppressWarnings("unused")
public class Thomas implements Essence, Worker {
    private static Log log = LogFactory.getLog(Thomas.class);

    private static final long THOMAS_DEFAULT_MAX_DELAY = 500;
    private static final String name = "Thomas";

    volatile EssenceState state = EssenceState.IDLE;
    Pet pet;
    Car car;

    Thread restThread;

    public Thomas(Pet pet, Car car) {
        this.pet = pet;
        this.car = car;
        log.debug(String.format("Creating %s", this));
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

    @Scheduled(cron="*/3 * * * * *")
    @Override
    public void rest() {
        if(state != EssenceState.IDLE) {
            log.warn(String.format("%s can't take a rest (%s)", name, state));
            return;
        }
        try {
            state = EssenceState.REST;
            restThread = Thread.currentThread();
            log.info(String.format("%s takes a rest", name));
            pet.play(this);
            Thread.sleep(ThreadLocalRandom.current().nextLong(THOMAS_DEFAULT_MAX_DELAY));
        } catch (InterruptedException e) {
            log.warn(String.format("%s rest is interrupted", name));
        } finally {
            restThread = null;
            state = EssenceState.IDLE;
            log.debug(String.format("%s rest is done", name));
        }
    }

    @Scheduled(cron="*/10 * * * * *")
    @Override
    public void sleep() {
        switch(state) {
            case SLEEP:
            case BUSY:
                log.error(String.format("%s can't sleep (%s)", name, state));
                return;
            case REST:
                if(restThread != null) restThread.interrupt();
            default:
                try {
                    state = EssenceState.SLEEP;
                    log.info(String.format("%s sleeps", name));
                    Thread.sleep(ThreadLocalRandom.current().nextLong(THOMAS_DEFAULT_MAX_DELAY * 5));
                } catch (InterruptedException e) {
                    log.error(String.format("%s sleep is interrupted", name));
                } finally {
                    state = EssenceState.IDLE;
                    log.debug(String.format("%s wakes up", name));
                }
        }
    }

    @Override
    public String toString() {
        return String.format("%s={name='%s', state=%s}", this.getClass().getSimpleName(), name, state);
    }
}
