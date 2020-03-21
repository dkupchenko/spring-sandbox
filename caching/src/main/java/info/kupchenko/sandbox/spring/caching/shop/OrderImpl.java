package info.kupchenko.sandbox.spring.caching.shop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс Order является реализацией абстракции Заказ
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 21.03.2020
 * Last review on 21.03.2020
 */
public class OrderImpl implements Order {
    // идентификатор Покупателя
    private final long buyerId;
    // время формирования Заказа
    private final LocalDateTime dt;
    // список Товаров в корзине
    private final List<Product> basket = new ArrayList<>();

    /**
     * Full-args constructor
     * @param buyerId идентификатор Покупателя
     * @param dt время формирования Заказа
     */
    public OrderImpl(long buyerId, LocalDateTime dt) {
        this.buyerId = buyerId;
        this.dt = dt;
    }

    /**
     * Возвращает идентификатор Покупателя
     * @return идентификатор Покупателя
     */
    @Override
    public long getBuyerId() {
        return buyerId;
    }

    /**
     * Возвращает время формирования Заказа
     * @return время Заказа
     */
    @Override
    public LocalDateTime getDt() {
        return dt;
    }

    /**
     * Возвращает корзину Закза
     * @return список Товаров
     */
    @Override
    public List<Product> getBasket() {
        return basket;
    }

    /**
     * добавляет Товар в корзину
     * @param product новый Товар в Заказе
     */
    @Override
    public void add(Product product) {
        basket.add(product);
    }

    /**
     * Сравнение экземпляров класса (учитывается идентификатор Покупателя и время заказа)
     * @param o сравниваемый экземпляр
     * @return true если экземляры равны, false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderImpl order = (OrderImpl) o;
        return buyerId == order.buyerId &&
                dt.equals(order.dt);
    }

    /**
     * Вычисление hash экземпляра класса
     * @return hash для данного экземпляра
     */
    @Override
    public int hashCode() {
        return Objects.hash(buyerId, dt);
    }

    /**
     * Строковое представление экземпляра класса
     * @return строка вида "%s{buyerId=%d, dt=%s, products(%d)}"
     */
    @Override
    public String toString() {
        return String.format("%s{buyerId=%d, dt=%s, products(%d)}",
                this.getClass().getSimpleName(), buyerId, dt, basket.size());
    }
}
