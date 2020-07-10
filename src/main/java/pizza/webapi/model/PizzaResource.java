package pizza.webapi.model;

import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
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
 * klase PizzaResourceAssembler, która rozszerza ResourceAssemblerSupport<Pizza, PizzaResource>
 */


public class PizzaResource extends RepresentationModel<PizzaResource> {


    public PizzaResource(Pizza pizza) {
        this.name = pizza.getName();
        this.createdAt = pizza.getCreatedAt();
        this.ingredients = new IngredientResourceAssembler().toCollectionModel(pizza.getIngredients());
    }

    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    @Getter
    private final CollectionModel<IngredientResource> ingredients;
}
