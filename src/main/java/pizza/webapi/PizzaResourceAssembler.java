package pizza.webapi;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pizza.domain.Pizza;

public class PizzaResourceAssembler extends RepresentationModelAssemblerSupport<Pizza, PizzaResource> {

    public PizzaResourceAssembler() {
        super(DesignPizzaRestController.class, PizzaResource.class);
    }

    @Override
    protected PizzaResource instantiateModel(Pizza entity) {
        return new PizzaResource(entity);
    }

    @Override
    public PizzaResource toModel(Pizza entity) {
        return createModelWithId(entity.getId(), entity);
    }


}
