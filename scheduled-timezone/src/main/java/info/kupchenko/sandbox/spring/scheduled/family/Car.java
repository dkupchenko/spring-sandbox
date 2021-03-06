package info.kupchenko.sandbox.spring.scheduled.family;

/**
 * The Car ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
public interface Car {
    String model();
    long price();
    void check(Essence sender);
    void move(Essence sender) throws InterruptedException;
}
