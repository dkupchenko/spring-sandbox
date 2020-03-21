package info.kupchenko.sandbox.spring.caching.shop;

import java.util.List;

/**
 * Интерфейс Shop описывает абстракцию Магазин
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
public interface Shop {
    /**
     * Возвращает Ассортимент Товаров в Магазине
     * @return список Товаров
     */
    List<Product> findAll();

    /**
     * обрабатывает Заказ Покупателя
     * @param order экземпляр Заказа
     */
    void order(Order order);

    /**
     * Логгирование статистики по обработанным Заказам
     */
    @SuppressWarnings("unused")
    void logOrders();
}
