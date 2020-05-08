package pizza.repositories;

import pizza.domain.Order;


public interface OrderRepository {

    Order save(Order order);
}
