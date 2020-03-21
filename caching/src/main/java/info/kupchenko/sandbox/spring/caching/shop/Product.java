package info.kupchenko.sandbox.spring.caching.shop;

/**
 * Интерфейс Product является абстракцией Товара
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
public interface Product {
    /**
     * Возвращает идентификатор Товара
     * @return идентификатор Товара
     */
    long getId();

    /**
     * Возвращает цену Товара
     * @return цена Товара
     */
    float getPrice();
}
