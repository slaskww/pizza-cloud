package pizza.webapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizza.domain.Pizza;
import pizza.repositories.jpa.JpaPizzaRepository;
import pizza.webapi.model.PizzaResource;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping(path = "/api/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignPizzaRestController {

    private  JpaPizzaRepository jpaPizzaRepository;

    public DesignPizzaRestController(JpaPizzaRepository jpaPizzaRepository) {
        this.jpaPizzaRepository = jpaPizzaRepository;
    }

    @GetMapping(path = "/recent")
   public CollectionModel<PizzaResource> recentPizzas(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Pizza> recents = jpaPizzaRepository.findAll(page).getContent();
      //  CollectionModel<EntityModel<Pizza>> recentResources = CollectionModel.wrap(recents);
      //  return recentResources.add(new Link("https://localhost:8080/api/design/recent", "recents"));
        CollectionModel<PizzaResource> recentResources = new PizzaResourceAssembler().toCollectionModel(recents);
        return recentResources.add(WebMvcLinkBuilder.linkTo(DesignPizzaRestController.class).slash("recent").withRel("recents"));

    }

    @GetMapping(path = "/{id}")
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
    }
}
