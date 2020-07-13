package pizza.webapi.model;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pizza.domain.Ingredient;

public class IngredientResource extends RepresentationModel<IngredientResource> {

    @Getter
    private final String name;

    @Getter
    private final Ingredient.Type type;


    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
