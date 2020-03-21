package info.kupchenko.sandbox.spring.caching.shop;

import java.util.List;

/**
 * Интерфейс Shop описывает базовый функционал Магазина
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
public interface Shop {
    /**
     * Возвращает ассортимент Тоаров в Магазине
     * @return список Товаров
     * @throws InterruptedException в случае окончания работы приложения
     */
    List<Product> findAll() throws InterruptedException;

    /**
     * формирует заказ на покупку списка Товаров
     * @param basket список Товаров в заказе
     * @throws InterruptedException в случае окончания работы приложения
     */
    void order(List<Product> basket) throws InterruptedException;
}
