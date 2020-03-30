package info.kupchenko.sandbox.spring.vertx;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Config ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 28.03.2020
 * Last review on 28.03.2020
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "info.kupchenko.sandbox.spring.vertx")
public class Config {

}
