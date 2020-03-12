package info.kupchenko.sandbox.spring.circling.family;

import org.springframework.scheduling.annotation.Async;

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
public class John implements Husband {
    private static final long JOHN_DEFAULT_MAX_DELAY = 500;
    private static final long MAX_AMOUNT = 1000L;
    Thread foregroundThread;
    String name;
    Pet pet;
    Car car;
    Wife wife;

    public John(Pet pet) {
        this.pet = pet;
        name = "John";
        System.out.println(this);
        pet.stroke(this);
        System.out.println(String.format("John looks on car: %s", car));
        System.out.println(String.format("John looks on wife: %s", wife));
    }

    // LifeCycle interface implementation
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
                Thread.sleep(ThreadLocalRandom.current().nextLong(JOHN_DEFAULT_MAX_DELAY));
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

    // Essence interface implementation
    @Override
    public String name() {
        return name;
    }

    @Async
    @Override
    public void rest() throws InterruptedException {
        System.out.println(String.format("[T-%d] %s takes a rest", Thread.currentThread().getId(), name));
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
        System.out.println(String.format("[T-%d] %s gives %d$ to %s", Thread.currentThread().getId(), name, amount, sender.name()));
        Thread.sleep(ThreadLocalRandom.current().nextLong(JOHN_DEFAULT_MAX_DELAY));
        return CompletableFuture.completedFuture(amount);
    }

    @Override
    public void setWife(Wife wife) {
        this.wife = wife;
    }

    // property DI
    @SuppressWarnings("unused")
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return String.format("Husband with name '%s'", name);
    }
}
