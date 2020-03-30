package info.kupchenko.sandbox.spring.vertx.entities;

import io.vertx.core.buffer.Buffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * The RateMessageCodecTest ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
public class RateMessageCodecTest {

    private RateMessageCodec codec = new RateMessageCodec();
    private Rate rate = new Rate(Currency.USD, LocalDateTime.now(), 55.5555f);

    @Test
    void checkWire() {
        Buffer buffer = Buffer.buffer();
        codec.encodeToWire(buffer, rate);
        Rate newRate = codec.decodeFromWire(0, buffer);
        Assertions.assertEquals(rate, newRate);
    }

    @Test
    void checkTransform() {
        Assertions.assertEquals(rate, codec.transform(rate));
    }

    @Test
    void checkName() {
        // System.out.println(codec.name());
        Assertions.assertEquals(codec.getClass().getCanonicalName(), codec.name());
    }

    @Test
    void checkSystemCodecId() {
        Assertions.assertEquals(-1, codec.systemCodecID());
    }
}
