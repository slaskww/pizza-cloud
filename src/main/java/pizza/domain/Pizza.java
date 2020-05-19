package pizza.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * API Java Bean Validation pozwala na sprawne zadeklarowanie regół walidacji danych w obiektach domeny, zwalniając nas
 * z konieczności tworzenia w kontrolerach kodu odpowiedzialnego za logike walidacji.
 * Weryfikacja danych opiera się na trzech krokach:
 *  1. zadeklarowanie reguł weryfikacji w klasie domeny (przy użyciu adnotacji)
 *  2. wskazanie w których metodach kontrolera powinna się odbyć weryfikacja danych podczas wykonywania żądań POST (przy użyciu adnotacj @ Valid przy argumencie metod)
 *  3. zmodyfikowanie widoku formularzy danych, tak aby wyświetlały informacje o błędach odkrytych podczas weryfikacji danych (th:errors="*{name}")
 *            <h3>Nadaj nazwę swojej pizzy</h3>
 *             <input type="text" th:field="*{name}">
 *             <span th:if="${#fields.hasErrors('name')}" th:errors="*{name}">Błąd nazwy</span>
 *
 *
 *  Chcąc zadeklarowac klasę jako encję JPA, musimy oznaczyć ją adnotacją @ Entity.
 *  Kolejną rzeczą jest oznaczenie właściwości będącej identyfikatorem encji adnotacją @ Id
 *  Adnotacja @ GeneratedValue z atrybutem AUTO spowoduje, że baza danych zostanie użyta do automatycznego wygenerowania wartości identyfikatora.
 *
 *  Adnotacja @ ManyToMany deklaruje związek między obiektem Pizza a listą obiektów Ingredient.
 *  Taka relacja wiele-do-wiele oznacza, że obiekt Pizza może być powiązaqny z wieloma obiektami Ingredient. Obiekt Ingredient możebyć natomiast powiązany z wieloma obiektami Pizza
 *
 *  Adnotacja @ PrePersist sprawi, że metoda zostanie uruchomiona, a w konswkwencji przypisana zostanie wartość właściwości createdAt, zanim nastąpi trwały zapis obiektu Pizza.
 *
 */

@Data
@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Nazwa powinna zawierać co najmniej 5 znaków")
    private String name;

    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "Wybierz co najmniej jeden składnik")
    private List<Ingredient> ingredients = new ArrayList<>();

    private Date createdAt;

    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
