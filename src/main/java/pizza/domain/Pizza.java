package pizza.domain;

import lombok.Data;

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
 */

@Data
public class Pizza {

    private Long id;
    private Date createdAt;
    @NotNull
    @Size(min = 5, message = "Nazwa powinna zawierać co najmniej 5 znaków")
    private String name;
    @Size(min = 1, message = "Wybierz co najmniej jeden składnik")
    private List<String> ingredients = new ArrayList<>();
}
