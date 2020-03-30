package info.kupchenko.sandbox.spring.vertx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The VertxApp ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 16.03.2020
 * Last review on 16.03.2020
 */
public class VertxApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}