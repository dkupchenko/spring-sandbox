package info.kupchenko.sandbox.spring.circling;

import org.springframework.stereotype.Component;

/**
 * The DeLorean ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 08.03.2020
 * Last review on 08.03.2020
 */
@Component
@SuppressWarnings("unused")
public class DeLorean extends StatedBean implements Car {
    String model;
    long price = 50000;

    DeLorean() {
        super();
        model = "DeLorean";
        System.out.println(this);
    }

    @Override
    public void onPostConstruct() {
        model = "DeLorean DMC-12";
        System.out.println(this);
    }

    @Override
    public void onContextRefresh() {
        System.out.println(this);
    }

    @Override
    public String model() {
        return model;
    }

    @Override
    public long price() {
        return price;
    }

    @Override
    public void move(Essence sender) {
        System.out.println(String.format("'%s' takes a trip by '%s'", sender.name(), model));
    }

    @Override
    public String toString() {
        return String.format("Car '%s' costs %d, %s", model, price, super.toString());
    }
}
