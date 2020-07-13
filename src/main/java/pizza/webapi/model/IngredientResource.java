package pizza.webapi.model;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pizza.domain.Ingredient;

/**
 * Klasa IngredientResource jest klasą narzędziową, która konwertuje obiekt Ingredient na obiekt IngredientResource. Nowy obiekt tym się będzie różnił of Ingredient, że pozwoli
 * na przekazywanie łączy (Link). Dzięki temu, że klasa rozszerza  RepresentationModel<>, dziedziczy listę obiektów Link (łączy), oraz metod do zarządzania tą listą.
 * W obiekcie IngredientResource pominęliśmy właściwość id z obiektu Ingredient. Zależy nam bowiem na tym, by nie udostępniać w API identyfikatorów związanych z bazą danych.
 * Takim identyfikatorem dla klienta API będzie łącze self zasobu.
 * Teraz w IngredientRestControllerze obiekt EntityModel<Ingredient> będziemy mogli zastąpić obiektem IngredientResource.
 *
 * Żeby można było łatwo przekonwertować listę obiektów Ingredient na obiekt CollectionModel<IngredientResource> musimy posłużyć się komponentem asemblera zasobu. W tym celu tworzymy
 * klase IngredientResourceAssembler, która rozszerza  RepresentationModelAssemblerSupport<Ingredient, IngredientResource>
 */

public class IngredientResource extends RepresentationModel<IngredientResource> {

    @Getter
    private final String name;

    @Getter
    private final Ingredient.Type type;


    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
