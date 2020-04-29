package pizza.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Adnotacja @ Data z biblioteki Lombok nakazuje wygenerowanie wszystkich brakujacych metod klasy:
 * gettery, settery, toString, hashCode, equals, konstruktor bezparametrowy i konstruktor parametrowy
 */

@Data
@AllArgsConstructor
public class Ingredient {

    private String id;
    private String name;
    private Type type;

    public static enum Type{
        MEAT,
        VEGETABLE,
        CHEESE,
        SAUCE,
        DOUGH
    }

}
