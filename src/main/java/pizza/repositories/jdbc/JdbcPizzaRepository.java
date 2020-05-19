package pizza.repositories.jdbc;

import pizza.domain.Pizza;


public interface JdbcPizzaRepository {

    Pizza save(Pizza design);
}
