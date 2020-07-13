package pizza.webapi;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pizza.domain.Ingredient;
import pizza.webapi.model.IngredientResource;

/**
 * Klasa komponentu asemblera zasobu. Jest ona wykorzystywana do konwersji listy obiektów Pizza na obiekt CollectionModel<IngredientResource>.
 *
 * List<Ingredient> ingredients;
 * CollectionModel<IngredientResource> recentResources = new IngredientResourceAssembler().toCollectionModel(ingredients);
 *
 * Konstruktor domyślny klasy IngredientResourceAssembler informuje klasę nadrzędną (RepresentationModelAssemblerSupport), że dla ustalenia
 * bazowej ścieżki dostępu dla adresów URL w łączach Link powstających podczas tworzenia IngredientResource, zostanie wykorzystany obiekt IngredientRestController.
 *
 * Medoda instantiateModel() towrzy obiekt IngredientResource na podstawie obiektu Ingredient.
 * Metoda toModel() nakazuje utworzenie obiektu IngredientResource na podstawie obiektu Ingredient (wewnętrznie wywołuje metodę instantiateModel), a następnie dodaje do niego łącze self (Link) Z adresem URL pochodzącym z właściwości id obiektu Ingredient.
 *
 *
 * Metoda toCollectionModel() otrzymuje listę obiektów Ingredient, przeprowadza iterację po obiektach Ingredient i dla każdego z nich wywołuje metodę toModel() konwertując instancę Ingredient na obiekt IngredientResource.
 * Następnie tworzy kolekcję obiektów IngredientResource w postaci CollectionModel<IngredientResource>.
 */


public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResource> {


    public IngredientResourceAssembler() {

        super(IngredientRestController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toModel(Ingredient entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected IngredientResource instantiateModel(Ingredient entity) {
        return new IngredientResource(entity);
    }

    @Override
    public CollectionModel<IngredientResource> toCollectionModel(Iterable<? extends Ingredient> entities) {
        return super.toCollectionModel(entities);
    }
}
