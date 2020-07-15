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


/**
 * Adnotacja RepositoryRestController oferowana przez Spring Data REST sprawia, że mapowanie ścieżki dostępu do zasobów kontrolera nastąpi wg. konfiguracji ścieżki bazowej
 * punktów końcowych Spring Data REST (skonfigurowanej w application.properties). Adnotacja ta nie zawiera w sobie adnotacji @ResponseBody, więc należy pamiętać o jej umieszczeniu
 * nad metodą zwracającą odpowiedź, lub opakować odpowiedź w obiekt ResponseEntity<>.
 * Spring Data REST udostępnia REST API 'out of the box', bazując na inerfejsach repozytoriów. Jeśli więc zależy nam na udostępnieniu punktów końcowych przeprowadzających operacje CRUD,
 * jest on w stanie w zupełności zastąpić kontrolery restowe.
 * W naszym przypadku Springowe Data REST endpointy zostały wzbogacone o ścieźkę do ostatnio dodanych obiektów Pizza (/api/pizzas/recent). W prawdzie Spring Data REST umożliwia wykonanie żądania
 * do zasobu z opcjonalnymi parametrami (page,size,sort), np: "/api/pizzas/?sort=createdAd,desc&page=0&size=12", ale powoduje to zaśmiecanie kodu i wymusza na kliencie definiowanie parametrów żądania 'na stale'.
 * Nam zależałoby na tym, by klient odszukał zwięzły adres URL z ostatnio dodanymi pizzami (/api/pizzas/recent) na liście łączy Spring Data w punkcie końcowym /api/pizzas.
 * Domyślnie własne endpointy nie zostają dodane i zwrócone razem z endpointami Spring Data. By takie łącza dodać do dostarczanej automatycznie listy,
 * należy posłużyć się komponentem procesora zasobu. Służy on do przeprowadzania operacji na zasobach zanim zostana one zwrócone przez API. Taki komponent bean utworzyliśmy w klasie RestConfig.
 */

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
