package info.kupchenko.sandbox.spring.scheduled.family;

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
    EssenceState state();
    void rest() throws InterruptedException;
    void sleep() throws InterruptedException;
}
