package info.kupchenko.sandbox.spring.circling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    String name;
    Pet pet;
    @Autowired
    Car car;
    @Autowired
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
    public void onContextRefresh() {
        System.out.println(toString());
        pet.stroke(this);
        car.check(this);
        System.out.println(String.format("%s: Hi, %s", name,  wife.name()));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void rest() throws InterruptedException {
        System.out.println(String.format("%s takes a rest", name));
        Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
        pet.play(this);
        car.move(this);
    }

    @Override
    public long getMoney() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
        long amount = ThreadLocalRandom.current().nextLong(MAX_AMOUNT);
        System.out.println(String.format("%s gives %d $", name, amount));
        return amount;
    }

    @Override
    public String toString() {
        return String.format("Husband with name '%s', %s", name, super.toString());
    }
}
