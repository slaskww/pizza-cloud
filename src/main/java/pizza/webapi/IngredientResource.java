package pizza.webapi;

import org.springframework.hateoas.RepresentationModel;
import pizza.domain.Ingredient;

public class IngredientResource extends RepresentationModel<IngredientResource> {

    private final String name;
    private final Ingredient.Type type;

    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
