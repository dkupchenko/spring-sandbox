package info.kupchenko.sandbox.spring.caching.shop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
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
@SuppressWarnings("unused")
public class ShopImpl implements Shop {
    // для логгирования
    private static final Log log = LogFactory.getLog(ShopImpl.class);
    // лок для синхронизации изменения счётчиков статистики
    private static final Object lock = new Object();
    // обнуляемый счетчик Заказов
    private long requests = 0;
    // накапливаемый счётчик Заказов
    private long allRequests = 0;
    // Ассортимент Товаров в Магазине
    Stock stock;

    /**
     * Инжекция Ассортимента через конструктор
     */
    public ShopImpl(Stock stock) {
        this.stock = stock;
    }

    /**
     * Возвращает Ассортимент Товаров в Магазине
     * @return список Товаров
     */
    @Override
    public List<Product> findAll() {
        return stock.findAll();
    }

    /**
     * обрабатывает Заказ Покупателя
     * @param order экземпляр Заказа
     */
    @Override
    public void order(Order order) {
        synchronized (lock) {
            requests++;
            allRequests++;
        }
        stock.order(order);
    }

    /**
     * Логгирование статистики по обработанным Заказам
     */
    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    @Override
    public void logOrders() {
        long tmpRequests;
        long tmpAllRequests;
        long tmpOrders;
        synchronized (lock) {
            tmpRequests = requests;
            tmpAllRequests = allRequests;
            tmpOrders = stock.ordersCount();
            requests = 0;
        }
        log.info(String.format("ORDERS STATISTICS: orders_count = %d (all = %d), products_count = %d", tmpRequests, tmpAllRequests, tmpOrders));
    }
}
