package pizza.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pizza.domain.Ingredient;
import pizza.repositories.jdbc.JdbcIngredientRepository;


@Repository
public class ImplJdbcJdbcIngredientRepository implements JdbcIngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ImplJdbcJdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query(
                "Select id, name, type From Ingredient",
                (resultSet, i) -> new Ingredient(resultSet.getString("id"), resultSet.getString("name"), Ingredient.Type.valueOf(resultSet.getString("type"))));
    }

    @Override
    public Ingredient findById(String id) {
        return jdbcTemplate.queryForObject(
                "Select id, name, type from Ingredient Where id=?" ,
                (rs, i) -> new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type"))),
                id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
                "Insert into Ingredient (id, name, type), values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType());
        return ingredient;
    }
}
