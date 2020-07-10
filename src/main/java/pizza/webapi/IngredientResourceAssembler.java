package pizza.webapi;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pizza.domain.Ingredient;
import pizza.webapi.model.IngredientResource;

public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResource> {


    public IngredientResourceAssembler() {

        super(DesignPizzaRestController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toModel(Ingredient entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected IngredientResource instantiateModel(Ingredient entity) {
        return new IngredientResource(entity);
    }
}
