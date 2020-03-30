package info.kupchenko.sandbox.spring.vertx.entities;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Класс RateMessageCodec служит для упаковки/распаковки POJO Rate при передаче его по EventBus
 *
 * @author Dmitry Kupchenko
 * @version 2.0
 * @since 2.0
 * Created on 30.03.2020
 * Last review on 30.03.2020
 */
public class RateMessageCodec implements MessageCodec<Rate, Rate> {
    /**
     * упаковывает экземпляр объекта Rate в Buffer
     *
     * @param buffer буфер для упаковки
     * @param rate котировка
     */
    @Override
    public void encodeToWire(Buffer buffer, Rate rate) {
        // add Currency data as (Int + String)
        buffer.appendInt(rate.getCurrency().name().length());
        buffer.appendString(rate.getCurrency().name());
        // add LocalDateTime data as (Long + Int)
        buffer.appendLong(rate.getTime().toEpochSecond(ZoneOffset.UTC));
        buffer.appendInt(rate.getTime().getNano());
        // add float value data as (Float)
        buffer.appendFloat(rate.getValue());
    }

    /**
     * распаковывает экземпляр Rate из Buffer
     *
     * @param startPosition  начальная позиция объекта в буфере
     * @param buffer буфер для распаковки
     * @return экземпляр объекта Rate
     */
    @Override
    public Rate decodeFromWire(int startPosition, Buffer buffer) {
        // текущая позиция нашего экземпляра в буфере
        int currentPosition = startPosition;

        // read (String) for Currency
        int stringLength = buffer.getInt(currentPosition);
        currentPosition += 4;
        Currency currency = Currency.valueOf(buffer.getString(currentPosition, currentPosition + stringLength));
        currentPosition += stringLength;

        // read (Long + Int) for LocalDateTime
        long epochSecond = buffer.getLong(currentPosition);
        currentPosition += 8;
        int nanos = buffer.getInt(currentPosition);
        currentPosition += 4;
        LocalDateTime time = LocalDateTime.ofEpochSecond(epochSecond, nanos, ZoneOffset.UTC);

        // read (Float) for value
        float value = buffer.getFloat(currentPosition);

        return new Rate(currency, time, value);
    }

    /**
     * Используется для передачи экземпляров локально в приложении, параметр передаётся без изменений
     *
     * @param rate исходная котировка
     * @return результирующая котировка
     */
    @Override
    public Rate transform(Rate rate) {
        return rate;
    }

    /**
     * Уникальное имя кодека, в качестве уникального имени используется полное имя класса кодека
     * @return полное имя класса кодека
     */
    @Override
    public String name() {
        return this.getClass().getCanonicalName();
    }

    /**
     * признак системного кодека, для ползовательских ВСЕГДА возвращаем -1
     *
     * @return -1
     */
    @Override
    public byte systemCodecID() {
        return -1;
    }
}
