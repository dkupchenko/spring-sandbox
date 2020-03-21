package info.kupchenko.sandbox.spring.caching.shop;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс Order представляет абстракцию Заказ
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 21.03.2020
 * Last review on 21.03.2020
 */
public interface Order {
    /**
     * Возвращает идентификатор Покупателя
     * @return идентификатор Покупателя
     */
    long getBuyerId();

    /**
     * Возвращает время формирования Заказа
     * @return время Заказа
     */
    LocalDateTime getDt();

    /**
     * Возвращает корзину Закза
     * @return список Товаров
     */
    List<Product> getBasket();

    /**
     * добавляет Товар в корзину
     * @param product новый Товар в Заказе
     */
    void add(Product product);
}
