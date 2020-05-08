package pizza.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pizza.domain.Ingredient;
import pizza.domain.Pizza;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcPizzaRepository implements PizzaRepository{

    private JdbcTemplate jdbcTemplate;

    public JdbcPizzaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Pizza save(Pizza design) {
        Long pizzaId = savePizzaInfo(design);
        design.setId(pizzaId);
        for (Ingredient ingredient : design.getIngredients()){
           saveIngredientToPizza(ingredient, pizzaId);
        }
        return design;
    }


    private Long savePizzaInfo(Pizza design) {
        design.setCreatedAt(new Date());
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                "Insert into Pizza(name, createdAt) values(?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        ).newPreparedStatementCreator(
                Arrays.asList(
                        design.getName(),
                        new Timestamp(design.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToPizza(Ingredient ingredient, Long pizzaId) {
        jdbcTemplate.update("Insert into Pizza_Ingredients(pizza, ingredient) values(?, ?)",
                pizzaId,
                ingredient.getId());
    }
}
