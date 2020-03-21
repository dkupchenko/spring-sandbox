package info.kupchenko.sandbox.spring.caching.shop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс BuyersSetImpl создаёт BUYERS_COUNT экземпляров покупателей,
 * стартует/останавливает их во время жизненного цикла приложения,
 * а так же собирает статитстику по профилированию этапов создания заказа каждого покупателя;
 * Класс не зависит от реализации интерфейса Buyer, DI осуществляется Spring'ом
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
abstract public class BuyersSetImpl implements BuyersSet {
    // количество покупателей
    private static final int BUYERS_COUNT = 100;
    // для логгирования
    private static final Log log = LogFactory.getLog(BuyersSetImpl.class);
    // признак запущен/остановлен
    private boolean running = false;
    // Список покупателей
    List<Buyer> buyers = new ArrayList<>();

    /**
     * Конструктор по умолчанию, заполняет список покупателей с помощью абстракного метода getBuyer()
     */
    public BuyersSetImpl() {
        for(int i = 0; i < BUYERS_COUNT; i++) {
            buyers.add(getBuyer());
        }
    }

    /**
     * Перегрузка данного метода осуществляет инжекцию бинов (в {@link info.kupchenko.sandbox.spring.caching.Config})
     * @return новый экземпляр покупателя
     */
    public abstract Buyer getBuyer();

    /**
     * Старт при обновлении контекста осуществляется с помощью аннотации @AutoStartupLifecycle в Config
     */
    @Override
    public void start() {
        if(running) return;
        running = true;
        for (Buyer buyer: buyers)
            buyer.start();
    }

    /**
     * Стоп при остановке JVM осуществляется с помощью аннотации @AutoStartupLifecycle в Config
     */
    @Override
    public void stop() {
        if(!running) return;
        running = false;
        for (Buyer buyer: buyers)
            buyer.stop();
    }

    /**
     * Имлементация интерфейса Lifecycle
     * @return true если выполняется, false в противном случае
     */
    @Override
    public boolean isRunning() {
        return false;
    }

    /**
     * Сбор и логгирование профилирования;
     * по всем покупателям периодически собирается статистика, вычисляется среднее и записывается в лог в формате
     * "PROFILING: findAll = %d ns, buy = %d ns"
     */
    @SuppressWarnings("unused")
    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    @Override
    public void logProfiling() {
        long sumFindAll = 0;
        long sumBuy = 0;
        for (Buyer buyer: buyers) {
            sumFindAll = sumFindAll + buyer.getFindAllProfiling();
            sumBuy = sumBuy + buyer.getOrderProfiling();
        }
        log.info(String.format("PROFILING: findAll = %d ns, order = %d ns", sumFindAll / buyers.size(), sumBuy / buyers.size()));
    }
}
