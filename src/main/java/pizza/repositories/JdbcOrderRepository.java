package pizza.repositories;

import org.springframework.stereotype.Repository;
import pizza.domain.Order;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    @Override
    public Order save(Order order) {
        return null;
    }
}
