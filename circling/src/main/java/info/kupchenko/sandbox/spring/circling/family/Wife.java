package info.kupchenko.sandbox.spring.circling.family;

/**
 * The Wife ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
public interface Wife extends Essence {
    void smile() throws InterruptedException;
    void setHusband(Husband husband);
}
