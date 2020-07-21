package pizza.webapi.clientmock;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;
import pizza.domain.Ingredient;
import pizza.repositories.jpa.JpaIngredientRepository;

import java.net.URI;
import java.util.Collection;

@Configuration
public class IngredientRestAPIConsumer {

    private final RestTemplate restTemplate;
    private final Traverson traverson;
    private final JpaIngredientRepository ingredientRepository;

    public IngredientRestAPIConsumer(RestTemplate restTemplate, Traverson traverson, JpaIngredientRepository ingredientRepository) {
        this.restTemplate = restTemplate;
        this.traverson = traverson;
        this.ingredientRepository = ingredientRepository;
    }

    public void putIngredient(Ingredient ingredient){

        restTemplate.put("https://localhost:8080/api/ingredients/{id}", ingredient, ingredient.getId());
    }

    public Ingredient postIngredient(Ingredient ingredient){
        return restTemplate.postForObject("https://localhost:8080/api/ingredients", ingredient, Ingredient.class);
    }

    public void deleteIngredient(Ingredient ingredient){
        restTemplate.delete("https://localhost:8080/api/ingredients/{id}", ingredient.getId());
    }

    public Collection<Ingredient> getAllIngredients(){

        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};

       CollectionModel<Ingredient> ingredients =  traverson.follow("ingredients").toObject(ingredientType);
       return ingredients.getContent();
    }

    public Collection<Ingredient> getRecentIngredients(){
        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};

        CollectionModel<Ingredient> recentIngredients =  traverson.follow("ingredients").follow("recent").toObject(ingredientType);
        return recentIngredients.getContent();
    }
}
