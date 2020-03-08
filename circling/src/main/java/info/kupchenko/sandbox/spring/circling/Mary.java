package info.kupchenko.sandbox.spring.circling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    Pet pet;
    @Autowired
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
    public void onContextRefresh() {
        System.out.println(toString());
        pet.stroke(this);
        System.out.println(String.format("%s: Hi, %s", name,  husband.name()));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void rest() throws InterruptedException {
        System.out.println(String.format("%s takes a rest", name));
        Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
        System.out.println(String.format("%s: %s, give me please some money...", name, husband.name()));
        long amount = husband.getMoney();
        System.out.println(String.format("%s: Hmm, only %d $ ...", name, amount));
    }

    @Override
    public void smile() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextLong(DEFAULT_MAX_DELAY));
        System.out.println(String.format("%s smiles", name));
    }

    @Override
    public String toString() {
        return String.format("Wife with name '%s', %s", name, super.toString());
    }
}
