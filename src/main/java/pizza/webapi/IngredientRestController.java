package pizza.webapi;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizza.domain.Ingredient;
import pizza.repositories.jpa.JpaIngredientRepository;
import pizza.webapi.model.IngredientResource;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "/api/ingredient", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientRestController {


    public final JpaIngredientRepository ingredientRepository;

    public IngredientRestController(JpaIngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResource> getById(@PathVariable(value = "id", required = true) String id){

        Optional<Ingredient> optIngredient = ingredientRepository.findById(id);

        if(optIngredient.isPresent()){

            IngredientResource ingredient = new IngredientResource(optIngredient.get());
            ingredient.add(WebMvcLinkBuilder.linkTo(methodOn(IngredientRestController.class).getById(id)).withRel("ingredient"));
            return new ResponseEntity<>(ingredient, HttpStatus.OK );
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public CollectionModel<IngredientResource> getAll(){
        List<Ingredient> ingredients = ingredientRepository.findAll();
        CollectionModel<IngredientResource> ingredientResources = new IngredientResourceAssembler().toCollectionModel(ingredients);
        return ingredientResources.add(WebMvcLinkBuilder.linkTo(IngredientRestController.class).withRel("ingredients"));
    }
}
