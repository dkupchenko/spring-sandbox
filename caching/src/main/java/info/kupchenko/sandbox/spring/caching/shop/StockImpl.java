package info.kupchenko.sandbox.spring.caching.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The StockImpl ...
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 21.03.2020
 * Last review on 21.03.2020
 */
abstract public class StockImpl implements Stock {
    // максимальная пауза при выполнении первой фазы
    private static final long FIND_ALL_MAX_DELAY = 500;
    // максимальная пауза при выполнении второй фазы
    private static final long ORDER_MAX_DELAY = 200;
    // количество товаров в ассортименте
    private static final int PRODUCTS_COUNT = 50;
    // ассортимент Товаров в Магазине
    List<Product> products = new ArrayList<>();

    /**
     * Конструктор по умолчанию, заполняет список товаров с помощью абстракного метода getProduct()
     */
    public StockImpl() {
        for(int i = 0; i < PRODUCTS_COUNT; i++)
            products.add(getProduct());
    }

    /**
     * Возвращает ассортимент Тоаров в Магазине, иммитируя "длительность" процесса
     * @return список Товаров
     * @throws InterruptedException в случае окончания работы приложения
     */
    @Override
    public List<Product> findAll() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextLong(FIND_ALL_MAX_DELAY));
        return products;
    }

    /**
     * обрабатывает заказ, иммитируя "длительность" процесса;
     * данная реализация предполагает, что товары никогда не заканчиваются
     * @param basket список Товаров в заказе
     * @throws InterruptedException в случае окончания работы приложения
     */
    @Override
    public void order(List<Product> basket) throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextLong(ORDER_MAX_DELAY * basket.size()));
    }

    abstract public Product getProduct();
}
