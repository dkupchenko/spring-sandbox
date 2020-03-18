package info.kupchenko.sandbox.spring.scheduled;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The ScheduledApplication ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 16.03.2020
 * Last review on 16.03.2020
 */
public class ScheduledApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring-context.xml");
    }
}