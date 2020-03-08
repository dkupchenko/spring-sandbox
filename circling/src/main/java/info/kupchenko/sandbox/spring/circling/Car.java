package info.kupchenko.sandbox.spring.circling;

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
    void move(Essence sender);
}
