package info.kupchenko.sandbox.spring.caching.shop;

import java.util.Objects;

/**
 * Класс ProductImpl является реализацией абстракции Товар
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
public class ProductImpl implements Product {
    // идентификатор Товара
    private final long id;
    // цена Товара
    private final float price;

    /**
     * All-args constructor
     * @param id уникальный идентификатор Товара
     * @param price цена товара
     */
    public ProductImpl(long id, float price) {
        this.id = id;
        this.price = price;
    }

    /**
     * Возвращает идентификатор Товара
     * @return идентификатор Товара
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Возвращает цену Товара
     * @return цена Товара
     */
    @Override
    public float getPrice() {
        return price;
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
     * @return стока вида "%s{id=%d, price=%.2f}"
     */
    @Override
    public String toString() {
        return String.format("%s{id=%d, price=%.2f}", this.getClass().getSimpleName(), id, price);
    }
}
