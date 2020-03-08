package info.kupchenko.sandbox.spring.circling;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The Config ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 06.03.2020
 * Last review on 06.03.2020
 */
@Configuration
@EnableAsync
@ComponentScan(basePackages = "info.kupchenko.sandbox.spring.circling")
public class Config {
}
