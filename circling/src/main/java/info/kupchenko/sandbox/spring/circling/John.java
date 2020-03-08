package info.kupchenko.sandbox.spring.circling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Husband ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
@Component
@SuppressWarnings("unused")
public class John extends StatedBean implements Husband {
    String name;
    Pet pet;
    @Autowired
    Car car;

    public John(Pet pet) {
        super();
        this.pet = pet;
        name = "Husband";
        System.out.println(toString());
        pet.stroke(this);
        // NullPointerException
        // car.move(this);
    }


    @Override
    public void onPostConstruct() {
        name = "John";
        System.out.println(toString());
        pet.stroke(this);
        car.move(this);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void onContextRefresh() {
        System.out.println(toString());
        pet.stroke(this);
        car.move(this);
    }

    @Override
    public void rest() {

    }

    @Override
    public void plan() {

    }

    @Override
    public String toString() {
        return String.format("Husband with name '%s', %s", name, super.toString());
    }
}
