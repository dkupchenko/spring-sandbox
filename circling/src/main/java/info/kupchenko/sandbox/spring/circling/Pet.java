package info.kupchenko.sandbox.spring.circling;

/**
 * The Pet ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 07.03.2020
 * Last review on 07.03.2020
 */
public interface Pet  extends Essence {
    void stroke(Essence sender);
    void play(Essence sender) throws InterruptedException;
}
