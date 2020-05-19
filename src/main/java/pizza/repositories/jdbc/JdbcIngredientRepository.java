package pizza.repositories.jdbc;

import pizza.domain.Ingredient;

public interface JdbcIngredientRepository {

    Iterable<Ingredient> findAll();
    Ingredient findById(String id);
    Ingredient save(Ingredient ingredient);
}
