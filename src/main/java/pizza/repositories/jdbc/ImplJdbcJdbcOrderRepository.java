package pizza.repositories.jdbc;

import org.springframework.stereotype.Repository;
import pizza.domain.Order;

@Repository
public class ImplJdbcJdbcOrderRepository implements JdbcOrderRepository {
    @Override
    public Order save(Order order) {
        return null;
    }
}
