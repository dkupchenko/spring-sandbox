package info.kupchenko.sandbox.spring.vertx.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  Класс CurrencyRate описывает сущность котировки валюты
 *
 * @author Dmitry Kupchenko
 * @version 1.0
 * Created on 29.03.2020
 * Last review on 29.03.2020
 */
public class Rate {
    /**
     * валюта котировки
      */
    private final Currency currency;
    /**
     * локальное время котировки
     */
    private final LocalDateTime time;
    /**
     * значение котировки
     */
    private final float value;

    /**
     * Конструктор котировок валют, локальное время котировки считается равным времени создания объекта
     *
     * @param currency валюта котировки
     * @param value значение котировки
     */
    public Rate(Currency currency, float value) {
        Objects.requireNonNull(currency);
        this.currency = currency;
        this.time = LocalDateTime.now();
        this.value = value;
    }

    /**
     * возвращает валюту котировки
     *
     * @return валюта котировки
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * возвращает время котировки
     *
     * @return время котировки
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * возвращает значение котировки
     *
     * @return значение котировки
     */
    public float getValue() {
        return value;
    }

    /**
     * Сравнивает экземпляр класса с другим объектом
     *
     * @param currencyRate сравниваемый объект
     * @return true, если равны, и false в противном случае
     */
    @Override
    public boolean equals(Object currencyRate) {
        if (this == currencyRate) return true;
        if (currencyRate == null || getClass() != currencyRate.getClass()) return false;
        Rate that = (Rate) currencyRate;
        return Float.compare(that.value, value) == 0 &&
                this.currency == that.currency &&
                Objects.equals(time, that.time);
    }

    /**
     * возвращает хэш для экземпляра класса
     *
     * @return хэш экземпляра класса
     */
    @Override
    public int hashCode() {
        return Objects.hash(currency, time, value);
    }

    /**
     * возвращает строковое представление экземпляра класса
     * <b>ВАЖНО: формат строкового представления может быть изменён в следующих релизах</b>
     *
     * @return строковое представление экземпляра класса в виде "%s[%s]{%s: %.4f}"
     */
    @Override
    public String toString() {
        return String.format("%s[%s]{%s: %.4f}", this.getClass().getSimpleName(), currency, time, value);
    }
}
