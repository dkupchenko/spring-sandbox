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

    public static void main(String[] args) throws InterruptedException {
        System.out.println(String.format("[T=%d] ------------ CirclingApplication creates a context -------------",
                Thread.currentThread().getId()));
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        System.out.println(String.format("[T=%d] ---------------- CirclingApplication is started ----------------",
                Thread.currentThread().getId()));
        context.start();
        Thread.sleep(5000L);
        context.stop();
        System.out.println(String.format("[T-%d] ---------------- CirclingApplication is stopped ----------------",
                Thread.currentThread().getId()));
    }
}
