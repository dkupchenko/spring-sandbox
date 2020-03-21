package info.kupchenko.sandbox.spring.caching.shop;

import org.springframework.context.Lifecycle;

/**
 * Интерфейс BuyersSet описывает фабрику по созданию покупателей;
 * фабрика ответственна за создание полезной нагрузки в виде покупателей,
 * старт/стопа нагрузки в ходе жизненного цикла приложения и
 * логгирования статистики по профилированию отдельных стадий формирования заказа
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 20.03.2020
 * Last review on 20.03.2020
 */
public interface BuyersSet extends Lifecycle {
    /**
     * сбор и логгирование профилирования всех фаз создания заказа в магазине
     */
    @SuppressWarnings("unused")
    void logProfiling();
}
