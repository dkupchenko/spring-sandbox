package info.kupchenko.sandbox.spring.caching.shop;

import java.util.Objects;

/**
 * Класс ProductImpl является реализацией бина Товар;
 * содержит только уникальный идентификатор в данной реализации
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
public class ProductImpl implements Product {
    // генератор уникального идентификатора
    private static long counter = 0;
    // идентификатор
    private long id;

    /**
     * Конструктор по умолчанию, создаёт экземпляр с новым уникальным идентификатором
     */
    public ProductImpl() {
        id = counter++;
    }

    /**
     * Сравнение экземпляров класса (учитываются только уникальные идентификаторы)
     * @param o сравниваемый экземпляр
     * @return true если равны
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImpl product = (ProductImpl) o;
        return id == product.id;
    }

    /**
     * Создает hash для данного экземпляра
     * @return hash данного экземпляра
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Строковое представление класса
     * @return стока вида "ProductImpl(123)"
     */
    @Override
    public String toString() {
        return String.format("%s(%d)", this.getClass().getSimpleName(), id);
    }
}
