package info.kupchenko.sandbox.spring.circling;

/**
 * The Essence ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
public interface Essence {
    String name();
    void rest() throws InterruptedException;
}
