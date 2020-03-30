package info.kupchenko.sandbox.spring.vertx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Класс VertxApp является точкой входа в приложение, используя в качестве конфигурации контекста
 * Java-аннотированную конфигурацию
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 29.03.2020
 * Last review on 30.03.2020
 */
public class VertxApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}