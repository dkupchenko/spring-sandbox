package info.kupchenko.sandbox.spring.caching.shop;

import java.util.List;

/**
 * Интерфейс Stock описывает функционал ассортимента Магазина
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
     * @throws InterruptedException в случае окончания работы приложения
     */
    List<Product> findAll() throws InterruptedException;

    /**
     * корректирует остатки Товаров в Магазине
     * @param basket список Товаров в заказе
     * @throws InterruptedException в случае окончания работы приложения
     */
    void order(List<Product> basket) throws InterruptedException;

}
