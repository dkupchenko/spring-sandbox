package info.kupchenko.sandbox.spring.circling;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The CirclingApplication ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
@EnableAsync
public class CirclingApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-context.xml");
        context.start();
        while(!Thread.interrupted()) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                System.out.println("CirclingApplication is interrupted");
                break;
            }
        }
        context.stop();
    }
}
