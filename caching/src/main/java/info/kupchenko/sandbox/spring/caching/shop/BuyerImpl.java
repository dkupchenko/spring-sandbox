package info.kupchenko.sandbox.spring.caching.shop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс BuyerImpl - реализация интерфейса Buyer, полезная нагрузка выполняется в отдельном потоке
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
abstract public class BuyerImpl implements Buyer {
    // для логгирования
    private static final Log log = LogFactory.getLog(BuyerImpl.class);
    // максимальная пауза между покупками
    private static final long MAX_BUYER_DELAY = 200;
    // генератор для id
    private static int counter = 0;
    // идентификатор покупателя
    private int id;
    // поток, в котором выполняется
    private Thread thread;
    // экземпляр Магазина
    private Shop shop;
    // объект-блокировка для фазы получения ассортимента в Магазине
    private final Object findAllLock = new Object();
    // сумма для вычисления среднего
    private long sumFindAll = 0;
    // количество для вычисления среднего
    private int countFindAll = 0;
    // объект-блокировка для фазы оформления списка покупок в Магазине
    private final Object orderLock = new Object();
    // сумма для вычисления среднего
    private long sumOrder = 0;
    // количество для вычисления среднего
    private int countOrder = 0;

    /**
     * Конструктор по умолчанию, получающий экземпляр Магазина и генерирующий уникальный id Покупателя
     * @param shop DI over constructor
     */
    public BuyerImpl(Shop shop) {
        id = counter++;
        this.shop = shop;
    }

    /**
     * абстрактный метод, переопределяется в {@link info.kupchenko.sandbox.spring.caching.Config} для инжектирования
     * экземпляров Заказов
     * @param buyerId
     * @param dt
     * @return
     */
    abstract public Order getOrder(long buyerId, LocalDateTime dt);

    /**
     * Полезная нагрузка в виде получения ассортимента Магазина и оформления заказа
     */
    void doShopping() {
        Order order = getOrder(id, LocalDateTime.now());
        log.debug("Start shopping");
        // фаза получения ассортимента товаров
        LocalDateTime start = LocalDateTime.now();
        List<Product> products = shop.findAll();
        LocalDateTime stop = LocalDateTime.now();
        // обновление статистики по первой фазе
        synchronized (findAllLock) {
            sumFindAll = sumFindAll + ChronoUnit.NANOS.between(start, stop);
            countFindAll++;
        }
        // добавление Товаров в Заказ
        for(int i = 0; i < ThreadLocalRandom.current().nextInt(1, products.size()); i++)
            order.add((products.get(ThreadLocalRandom.current().nextInt(0, products.size() - 1))));
        // фаза оформления заказа на список покупок
        start = LocalDateTime.now();
        shop.order(order);
        stop = LocalDateTime.now();
        // обновление статистики по второй фазе
        synchronized (orderLock) {
            sumOrder = sumOrder + ChronoUnit.NANOS.between(start, stop)/order.getBasket().size();
            countOrder++;
        }
        log.debug("Stop shopping");
    }

    /**
     * Безконечный цикл с периодическим функционалом полезной нагрузки, выполняется в отдельном потоке
     */
    @Async
    @Override
    public void start() {
        thread = Thread.currentThread();
        while(!Thread.interrupted()) {
            try {
                doShopping();
                Thread.sleep(ThreadLocalRandom.current().nextLong(MAX_BUYER_DELAY));
            } catch (InterruptedException e) {
                log.info(String.format("%s is interrupted", this));
                return;
            }
        }
    }

    /**
     * При закрытии приложения
     */
    @Override
    public void stop() {
        thread.interrupt();
    }

    /**
     * Выдача статистики по профилированию фазы получения ассортимента Магазина
     * @return интервал в наносекундах
     */
    @Override
    public long getFindAllProfiling() {
        synchronized (findAllLock) {
            long sum = sumFindAll;
            long count = countFindAll;
            sumFindAll = 0;
            countFindAll = 0;
            return sum / count;
        }
    }

    /**
     * Выдача статистики по профилированию фазы оформления заказа в Магазине
     * @return интервал в наносекундах
     */
    @Override
    public long getOrderProfiling() {
        synchronized (orderLock) {
            long sum = sumOrder;
            long count = countOrder;
            sumOrder = 0;
            countOrder = 0;
            return sum / count;
        }
    }

    /**
     * Сравнение экземпляров класса (учитывается только идентификатор id)
     * @param o сравниваемый экземпляр
     * @return true если экземляры равны, false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyerImpl buyer = (BuyerImpl) o;
        return id == buyer.id;
    }

    /**
     * Вычисление hash экземпляра класса
     * @return hash для данного экземпляра
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Строковое представление экземпляра класса
     * @return строка вида "BuyerImpl(123)"
     */
    @Override
    public String toString() {
        return String.format("%s(%d)", this.getClass().getSimpleName(), id);
    }
}
