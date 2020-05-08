package pizza.repositories;

import pizza.domain.Pizza;

public interface PizzaRepository {

    Pizza save(Pizza design);
}
