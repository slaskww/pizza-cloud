package pizza.webapi.model;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
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
 *
 * Adnotacja Relation służy do konfiguracji relacji użytej przy osadzaniu obiektów IngredientResource jako hiperłączy w odpowiedziach JSON (format HAL).
 * Domyślnie pole w JSON dla relacji z pojedyńczym zasobem przyjmuje nazwę klasy IngredientResource. Atrybut value umożliwia zmianę tej nazwy.
 * Analogicznie nazwa pola w JSON dla relacji z kolekcją zasobów to IngredientResourceList. Atrybut collectionRelation umożliwia zmianę tej nazwy.
 * Adnotacja ta zapobiega przyszłym problemom użytkowników API (wykorzystujących na stałe zdefiniowaną nazwę IngredientResourceList), jakie mogłyby wyniknąć ze zmiany nazwy klasy zasobu w ramach faktoryzacji.
 */

@Relation(value = "ingredient", collectionRelation = "ingredients")
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
