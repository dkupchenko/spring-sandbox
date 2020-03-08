package info.kupchenko.sandbox.spring.circling;

/**
 * The Husband ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 07.03.2020
 * Last review on 07.03.2020
 */
public interface Husband extends Essence {
    long getMoney(Essence sender) throws InterruptedException;
    void asyncMethod() throws InterruptedException;
}
