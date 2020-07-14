package pizza.webapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizza.domain.Order;
import pizza.domain.Pizza;
import pizza.props.config.PizzaProps;
import pizza.repositories.jpa.JpaPizzaRepository;
import pizza.webapi.model.PizzaResource;

import java.util.List;
import java.util.Optional;


@Slf4j
@RepositoryRestController
@CrossOrigin(origins = "*")
public class DesignPizzaRestController {

    private  JpaPizzaRepository jpaPizzaRepository;
    private PizzaProps pizzaProps;

    public DesignPizzaRestController(JpaPizzaRepository jpaPizzaRepository, PizzaProps pizzaProps) {
        this.jpaPizzaRepository = jpaPizzaRepository;
        this.pizzaProps = pizzaProps;
    }

    @GetMapping(path = "/pizzas/recent")
    @ResponseBody
   public CollectionModel<PizzaResource> recentPizzas(){
        PageRequest page = PageRequest.of(0, pizzaProps.getPageSize(), Sort.by("createdAt").descending());
        List<Pizza> recents = jpaPizzaRepository.findAll(page).getContent();
      //  CollectionModel<EntityModel<Pizza>> recentResources = CollectionModel.wrap(recents);
      //  return recentResources.add(new Link("https://localhost:8080/api/design/recent", "recents"));
        CollectionModel<PizzaResource> recentResources = new PizzaResourceAssembler().toCollectionModel(recents);
        return recentResources.add(WebMvcLinkBuilder.linkTo(DesignPizzaRestController.class).slash("recent").withRel("recents"));

    }

/*    @GetMapping(path = "/{id}")
    public ResponseEntity<PizzaResource> getById(@PathVariable("id") Long id){
        Optional<Pizza> optPizza = jpaPizzaRepository.findById(id);
        if(optPizza.isPresent()){
            return new ResponseEntity<>(new PizzaResource(optPizza.get())
                    .add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder
                                    .methodOn(DesignPizzaRestController.class)
                                    .getById(id))
                            .withRel("design")), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/new", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza postPizza(@RequestBody Pizza pizza){
        return jpaPizzaRepository.save(pizza);
    }*/

//@Bean
//    public RepresentationModelProcessor<PagedModel<EntityModel<Pizza>>> pizzaProcessor(EntityLinks links){
//    return new RepresentationModelProcessor<PagedModel<EntityModel<Pizza>>>() {
//        @Override
//        public PagedModel<EntityModel<Pizza>> process(PagedModel<EntityModel<Pizza>> model) {
//            return model.add(links.linkFor(Pizza.class).slash("recent").withRel("recents"));
//        }
//    };
//}
}
