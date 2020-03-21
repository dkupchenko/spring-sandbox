package info.kupchenko.sandbox.spring.caching.shop;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс  ShopImpl реализует базовый функционал Магазина
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
@Service
public class ShopImpl implements Shop {
    // ассортимент Товаров в Магазине
    Stock stock;

    /**
     * Инжекция ассортимента через конструктор
     */
    public ShopImpl(Stock stock) {
        this.stock = stock;
    }

    /**
     * Возвращает ассортимент Тоаров в Магазине
     * @return список Товаров
     * @throws InterruptedException в случае окончания работы приложения
     */
    @Override
    public List<Product> findAll() throws InterruptedException {
        return stock.findAll();
    }

    /**
     * формирует заказ на покупку списка Товаров
     * @param basket список Товаров в заказе
     * @throws InterruptedException в случае окончания работы приложения
     */
    @Override
    public void order(List<Product> basket) throws InterruptedException {
        stock.order(basket);
    }
}
