package info.kupchenko.sandbox.spring.circling;

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
public class Dog extends StatedBean implements Pet {
    String name;

    public Dog() {
        super();
        name = "Dog";
        System.out.println(toString());
    }

    @Override
    public void onPostConstruct() {
        name = "Rocky";
        System.out.println(toString());
    }

    @Override
    public void onContextRefresh() {
        System.out.println(toString());
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void rest() {

    }

    @Override
    public void stroke(Essence sender){
        System.out.println(String.format("'%s' strokes the '%s'", sender.name(), name));
    }

    @Override
    public void play(Essence sender) throws InterruptedException {
        long waitTime = ThreadLocalRandom.current().nextLong(500L);
        System.out.println(sender.name() + " is playing with " + name + " for a " + waitTime + " ms");
        Thread.sleep(waitTime);
    }

    @Override
    public String toString() {
        return String.format("%s with name '%s', %s", this.getClass().getSimpleName(), name, super.toString());
    }
}
