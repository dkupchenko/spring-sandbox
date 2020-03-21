package info.kupchenko.sandbox.spring.caching.shop;

import java.util.List;

/**
 * Интерфейс Stock описывает абстракцию Ассортимента в Магазине
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 21.03.2020
 * Last review on 21.03.2020
 */
public interface Stock {
    /**
     * Возвращает ассортимент Тоаров в Магазине
     * @return список Товаров
     */
    List<Product> findAll();

    /**
     * сохраняет и обрабатывает заказы
     * @param order Заказ
     */
    void order(Order order);

    /**
     * возвращает общее количество обработанных товаров
     * @return количество строк в таблице orders
     */
    long ordersCount();
}
