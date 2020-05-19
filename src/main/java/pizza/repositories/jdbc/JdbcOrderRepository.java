package pizza.repositories.jdbc;

import pizza.domain.Order;


public interface JdbcOrderRepository {

    Order save(Order order);
}
