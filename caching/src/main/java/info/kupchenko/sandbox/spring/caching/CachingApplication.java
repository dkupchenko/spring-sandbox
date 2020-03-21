package info.kupchenko.sandbox.spring.caching;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Класс CachingApplication содержит точку входа для запуска приложения
 * и создаёт контекст; вся часть конфигурации контекста содержится в
 * {@link Config} - конфигурации через аннотации, но "точка входа" выполнена
 * в виде xml-конфигурации со ссылкой на аннотированную часть
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 19.03.2020
 * Last review on 19.03.2020
 */
public class CachingApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-context.xml");
    }
}
