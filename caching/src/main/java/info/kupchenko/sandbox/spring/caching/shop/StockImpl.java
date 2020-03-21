package info.kupchenko.sandbox.spring.caching.shop;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

/**
 * Класс StockImpl имплементирует абстракцию Ассортимент
 *
 * @author by Dmitry Kupchenko
 * @version 1.0
 * Created on 21.03.2020
 * Last review on 21.03.2020
 */
abstract public class StockImpl implements Stock {
    // для работы с БД
    private final JdbcTemplate jdbcTemplate;

    /**
     * DI over constructor бина JdbcTemplate
     * @param jdbcTemplate бин JdbcTemplate
     */
    public StockImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * абстрактный метод;
     * необходим для инжекции Товаров в Ассортимент (см. {@link info.kupchenko.sandbox.spring.caching.Config})
     * @param id идентификатор Товара
     * @param price цена Товара
     * @return экземпляр Товара
     */
    abstract public Product getProduct(long id, float price);

    /**
     * Возвращает ассортимент Тоаров в Магазине
     * @return список Товаров
     */
    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(
                "select id, price from products",
                (resultSet, i) -> getProduct(resultSet.getLong("id"), resultSet.getFloat("price"))
        );
    }

    /**
     * сохраняет и обрабатывает заказы
     * @param order Заказ
     */
    @Override
    public void order(Order order) {
        if(order == null || order.getBasket().size() == 0) return;
        List<Product> basket = order.getBasket();
        jdbcTemplate.batchUpdate(
                "insert into orders (dt, buyer_id, product_id, price) values (?, ?, ?, ?)",
                basket,
                basket.size(),
                (ps, product) -> {
                    ps.setTimestamp(1, Timestamp.valueOf(order.getDt()));
                    ps.setLong(2, order.getBuyerId());
                    ps.setLong(3, product.getId());
                    ps.setFloat(4, product.getPrice());
                }
        );
    }

    /**
     * возвращает общее количество обработанных товаров
     * @return количество строк в таблице orders
     */
    @Override
    public long ordersCount() {
        Long result = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
        return (result != null) ? result : 0;
    }
}
