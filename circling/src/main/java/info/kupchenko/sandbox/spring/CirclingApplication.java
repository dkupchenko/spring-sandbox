package info.kupchenko.sandbox.spring;

import info.kupchenko.sandbox.spring.circling.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The CirclingApplication ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
public class CirclingApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}
