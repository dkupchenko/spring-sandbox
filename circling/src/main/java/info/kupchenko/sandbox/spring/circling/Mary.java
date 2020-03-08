package info.kupchenko.sandbox.spring.circling;

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
public class Mary extends StatedBean implements Wife {
    String name;
    Thread foregroundThread;
    Pet pet;
    Husband husband;

    Mary(Pet pet) {
        super();
        this.pet = pet;
        name = "Wife";
        System.out.println(toString());
        pet.stroke(this);
        System.out.println(String.format("Mary looks on husband: %s", husband));
    }

    @Override
    public void onPostConstruct() {
        name = "Mary";
        System.out.println(toString());
        pet.stroke(this);
        System.out.println(String.format("Mary looks on husband: %s", husband));
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
            System.out.println(String.format("[T-%d] %s: Hi, %s...", Thread.currentThread().getId(), name, husband.name()));
            foregroundThread = Thread.currentThread();
            while (!Thread.interrupted()) {
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
        try {
            System.out.println(String.format("[T-%d] %s takes a rest", Thread.currentThread().getId(), name));
            Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
            System.out.println(String.format("[T-%d] %s: %s, give me please some money...", Thread.currentThread().getId(), name, husband.name()));
            long amount = husband.getMoney(this).get();
            System.out.println(String.format("[T-%d] %s: Hmm, only %d$ ...", Thread.currentThread().getId(), name, amount));
        } catch (ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Async
    @Override
    public void smile() throws InterruptedException {
        System.out.println(String.format("[T-%d] %s smiles", Thread.currentThread().getId(), name));
        Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
    }

    @Override
    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    @Override
    public String toString() {
        return String.format("Wife with name '%s', %s", name, super.toString());
    }
}
