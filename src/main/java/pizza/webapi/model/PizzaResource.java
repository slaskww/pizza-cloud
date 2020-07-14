package pizza.webapi.model;

import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import pizza.domain.Pizza;
import pizza.webapi.IngredientResourceAssembler;

import java.util.Date;

/**
 * Klasa PizzaResource jest klasą narzędziową, która konwertuje obiekt Pizza na obiekt PizzaResource. Nowy obiekt tym się będzie różnił of Pizza, że pozwoli
 * na przekazywanie łączy (Link). Dzięki temu, że klasa rozszerza  RepresentationModel, dziedziczy listę obiektów Link (łączy), oraz metod do zarządzania tą listą.
 * W obiekcie PizzaResouce pominęliśmy właściwość id z obiektu Pizza. Zależy nam bowiem na tym, by nie udostępniać w API identyfikatorów związanych z bazą danych.
 * Takim identyfikatorem dla klienta API będzie łącze self zasobu.
 * Teraz w DesignPizzaRestControllerze obiekt EntityModel<Pizza> będziemy mogli zastąpić obiektem PizzaResource.
 *
 * Żeby można było łatwo przekonwertować listę obiektów Pizza na obiekt CollectionModel<PizzaResource> musimy posłużyć się komponentem asemblera zasobu. W tym celu tworzymy
 * klase PizzaResourceAssembler, która rozszerza RepresentationModelAssemblerSupport<Pizza, PizzaResource>
 *
 * Adnotacja Relation służy do konfiguracji relacji użytej przy osadzaniu obiektów IngredientResource jako hiperłączy w odpowiedziach JSON (format HAL).
 * Domyślnie pole w JSON dla relacji z pojedyńczym zasobem przyjmuje nazwę klasy IngredientResource. Atrybut value umożliwia zmianę tej nazwy.
 * Analogicznie nazwa pola w JSON dla relacji z kolekcją zasobów to IngredientResourceList. Atrybut collectionRelation umożliwia zmianę tej nazwy.
 * Adnotacja ta zapobiega przyszłym problemom użytkowników API (wykorzystujących na stałe zdefiniowaną nazwę IngredientResourceList), jakie mogłyby wyniknąć ze zmiany nazwy klasy zasobu w ramach faktoryzacji.
 */

@Relation(value = "pizza", collectionRelation = "pizzas")
public class PizzaResource extends RepresentationModel<PizzaResource> {

    private final IngredientResourceAssembler
            ingredientResourceAssembler = new IngredientResourceAssembler();

    public PizzaResource(Pizza pizza) {
        this.name = pizza.getName();
        this.createdAt = pizza.getCreatedAt();
        this.ingredients = ingredientResourceAssembler.toCollectionModel(pizza.getIngredients());
    }

    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    @Getter
    private final CollectionModel<IngredientResource> ingredients;
}
