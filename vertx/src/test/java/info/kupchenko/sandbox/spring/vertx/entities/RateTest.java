package info.kupchenko.sandbox.spring.vertx.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Класс RateTest тестирует сущность Rate
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
public class RateTest {
    /**
     * Проверка нормального создания котировки;
     * время котировки оценивается приблизительно (на создание экземпляра класса Rate отводится 50 мс)
     */
    @Test
    void normalCreate() {
        Currency currency = Currency.USD;
        float value = 55.5555f;
        long millisDelta = 50;

        // котрировки с value > 0
        LocalDateTime from = LocalDateTime.now();
        Rate rate = new Rate(currency, value);
        // System.out.println(String.format("%s vs %s", from, rate.getTime()));
        Assertions.assertEquals(currency, rate.getCurrency());
        Assertions.assertFalse(rate.getTime().isBefore(from));
        Assertions.assertTrue(Duration.between(from, rate.getTime()).toMillis() < millisDelta);
        Assertions.assertEquals(value, rate.getValue());


        // котрировки с value == 0
        from = LocalDateTime.now();
        rate = new Rate(currency, 0);
        // System.out.println(String.format("%s vs %s", from, rate.getTime()));
        Assertions.assertEquals(currency, rate.getCurrency());
        Assertions.assertFalse(rate.getTime().isBefore(from));
        Assertions.assertTrue(Duration.between(from, rate.getTime()).toMillis() < millisDelta);
        Assertions.assertEquals(0, rate.getValue());

        // котрировки с value < 0
        from = LocalDateTime.now();
        rate = new Rate(currency, -value);
        // System.out.println(String.format("%s vs %s", from, rate.getTime()));
        Assertions.assertEquals(currency, rate.getCurrency());
        Assertions.assertFalse(rate.getTime().isBefore(from));
        Assertions.assertTrue(Duration.between(from, rate.getTime()).toMillis() < millisDelta);
        Assertions.assertEquals(-value, rate.getValue());
    }

    /**
     * Проверка создания котировки с пустой валютой;
     * время котировки оценивается приблизительно: на создание экземпляра класса Rate отводится 50 мс
     */
    @Test
    void createWithEmptyCurrency() {
        float value = 55.5555f;
        Assertions.assertThrows(NullPointerException.class, () -> new Rate(null, value));
    }

    @Test
    void equalsAndHashCode() {
        Currency currency = Currency.USD;
        float value = 55.5555f;
        Rate rate = new Rate(currency, value);
        Rate anotherRate = new Rate(currency, value);

        Assertions.assertEquals(rate, rate);
        Assertions.assertEquals(rate.hashCode(), rate.hashCode());

        Assertions.assertNotEquals(rate, anotherRate);
        Assertions.assertNotEquals(rate.hashCode(), anotherRate.hashCode());
    }

    @Test
    void toStringTest() {
        Currency currency = Currency.USD;
        float value = 55.5555f;
        Rate rate = new Rate(currency, value);

        Assertions.assertNotNull(rate.toString());
        Assertions.assertNotEquals(0, rate.toString().length());
    }
}
