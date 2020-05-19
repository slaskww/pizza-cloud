package pizza.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Adnotacja @ Data z biblioteki Lombok nakazuje wygenerowanie wszystkich brakujacych metod klasy:
 * gettery, settery, toString, hashCode, equals, konstruktor bezparametrowy i konstruktor parametrowy
 *
 * Chcąc zadeklarowac klasę jako encję JPA, musimy oznaczyć ją adnotacją @ Entity.
 * Kolejną rzeczą jest oznaczenie właściwości będącej identyfikatorem encji adnotacją @ Id
 *
 * JPA wymaga od klasy encji konstruktora bezparametrowego. W związku z tym wykorzystujemy tu adnotację @ NoArgsConstructor.
 * Atrybut force = true wykorzystywany jest do przypisania warości null właściwościom oznaczonym jako final.
 *
 */

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity

public class Ingredient {

    @Id
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type{
        MEAT,
        VEGETABLE,
        CHEESE,
        SAUCE,
        DOUGH
    }

}
