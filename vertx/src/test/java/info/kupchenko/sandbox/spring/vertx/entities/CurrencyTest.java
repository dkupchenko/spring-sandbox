package info.kupchenko.sandbox.spring.vertx.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The CurrencyTest tests Currency enum
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
public class CurrencyTest {

    @Test
    void enumCount() {
        Assertions.assertEquals(1, Currency.values().length);
    }

    @Test
    void usdTest() {
        // check for Currency.USD(840, "USD", "US Dollar", "Доллар США");
        Currency currency = Currency.USD;

        Assertions.assertEquals(Currency.USD, currency);
        Assertions.assertEquals(840, currency.getIsoCode());
        Assertions.assertEquals("USD", currency.getIsoName());
        Assertions.assertEquals("US Dollar", currency.getEngName());
        Assertions.assertEquals("Доллар США", currency.getRuName());
        Assertions.assertNotNull(currency.toString());
        Assertions.assertNotEquals(0, currency.toString().length());
    }
}
