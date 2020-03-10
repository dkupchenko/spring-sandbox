package info.kupchenko.sandbox.spring.circling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Husband ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
@Component
@SuppressWarnings("unused")
public class John extends StatedBean implements Husband {
    private static final long MAX_AMOUNT = 1000L;
    Thread foregroundThread;
    String name;
    Pet pet;
    @Autowired
    Car car;
    Wife wife;

    public John(Pet pet) {
        super();
        this.pet = pet;
        name = "Husband";
        System.out.println(toString());
        pet.stroke(this);
        System.out.println(String.format("John looks on car: %s", car));
        System.out.println(String.format("John looks on wife: %s", wife));
    }

    @Override
    public void onPostConstruct() {
        name = "John";
        System.out.println(toString());
        pet.stroke(this);
        car.check(this);
        System.out.println(String.format("John looks on wife: %s", wife));
    }

    @Override
    public String name() {
        return name;
    }

    @Async
    @Override
    public void start() {
        try {
            if(isRunning()) return;
            System.out.println(String.format("[T-%d] %s IS STARTED", Thread.currentThread().getId(), name));
            System.out.println(String.format("[T-%d] %s: Hi, %s...", Thread.currentThread().getId(), name, wife.name()));
            foregroundThread = Thread.currentThread();
            while(!Thread.interrupted()) {
                System.out.println(String.format("[T-%d] %s wants to take rest", Thread.currentThread().getId(), name));
                rest();
                Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
            }
        } catch (InterruptedException e) {
            System.out.println(String.format("[T-%d] %s IS INTERRUPTED", Thread.currentThread().getId(), name));
        }
    }

    @Override
    public void stop() {
        if(!isRunning()) return;
        System.out.println(String.format("[T-%d] %s IS STOPPED", Thread.currentThread().getId(), name));
        foregroundThread.interrupt();
        foregroundThread = null;
    }

    @Override
    public boolean isRunning() {
        return (foregroundThread != null);
    }

    @Async
    @Override
    public void rest() throws InterruptedException {
        System.out.println(String.format("[T-%d] %s takes a rest", Thread.currentThread().getId(), name));
        Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
        pet.play(this);
        car.move(this);
        wife.smile();
    }

    @Async
    @Override
    public Future<Long> getMoney(Essence sender) throws InterruptedException {
        long amount = ThreadLocalRandom.current().nextLong(MAX_AMOUNT);
        System.out.println(String.format("[T-%d] %s gives %d$ to %s", Thread.currentThread().getId(), name, amount, sender.name()));
        Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
        return CompletableFuture.completedFuture(amount);
    }

    @Override
    public void setWife(Wife wife) {
        this.wife = wife;
    }

    @Override
    public String toString() {
        return String.format("Husband with name '%s', %s", name, super.toString());
    }
}
