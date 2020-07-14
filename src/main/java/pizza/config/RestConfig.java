package pizza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import pizza.domain.Order;
import pizza.domain.Pizza;

@Configuration
public class RestConfig {

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Pizza>>> pizzaProcesso(EntityLinks links) {
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
