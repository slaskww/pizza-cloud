package pizza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import pizza.domain.Order;
import pizza.domain.Pizza;

/**
 *  Domyślnie własne endpointy nie zostają dodane i zwrócone razem z endpointami Spring Data. By takie łącza dodać do dostarczanej automatycznie listy,
 *  należy posłużyć się komponentem procesora zasobu (RepresentationModelProcessor). Służy on do przeprowadzania operacji na zasobach
 *  zanim zostana one zwrócone przez API.
 *  Procesorem zasobu jest tutaj RepresentationModelProcessor, a zasob jest typu PagedModel<EntityModel<Pizza>>, czyli typu zwracanego przez punkt końcowy /api/pizzas.
 *  Po dodaniu do obiektu zasobu nowego łącza do '/api/pizzas/recent', znajdzie się ono na liście razem z automatycznie wygenerowanymi przez Spring Dada REST endpointami.
 *
 */

@Configuration
public class RestConfig {

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Pizza>>> pizzaProcessor(EntityLinks links) {
        return new RepresentationModelProcessor<PagedModel<EntityModel<Pizza>>>() {
            @Override
            public PagedModel<EntityModel<Pizza>> process(PagedModel<EntityModel<Pizza>> resources) {
                resources.add(links.linkFor(Pizza.class).slash("recent").withRel("recents"));
                return resources;
            }
        };
    }

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Order>>> orderProcessor(EntityLinks links){
        return new RepresentationModelProcessor<PagedModel<EntityModel<Order>>>() {
            @Override
            public PagedModel<EntityModel<Order>> process(PagedModel<EntityModel<Order>> model) {
                model.add(links.linkFor(Order.class).slash("recent").withRel("recents"));
                return model;
            }
        };
    }
}
