package pizza.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * API Java Bean Validation pozwala na sprawne zadeklarowanie regół walidacji danych w obiektach domeny, zwalniając nas
 * z konieczności tworzenia w kontrolerach kodu odpowiedzialnego za logike walidacji.
 * Weryfikacja danych opiera się na trzech krokach:
 *  1. zadeklarowanie reguł weryfikacji w klasie domeny (przy użyciu adnotacji)
 *  2. wskazanie w których metodach kontrolera powinna się odbyć weryfikacja danych podczas wykonywania żądań POST (przy użyciu adnotacj @ Valid przy argumencie metod)
 *  3. zmodyfikowanie widoku formularzy danych, tak aby wyświetlały informacje o błędach odkrytych podczas weryfikacji danych (th:errors="*{name}")
 *
 *           <label for="street">Ulica</label>
 *           <input id="street" th:field="*{street}">
 *           <span th:if="${#fields.hasErrors()}" th:errors="*{street}"></span>
 */

@Data
public class Order {

    private Long id;
    private Date orderedAt;
    @NotBlank(message = "Podaj imię i nazwisko")
    private String name;
    @NotBlank(message = "Podaj ulicę dostawy")
    private String street;
    @NotBlank(message = "Podaj nazwę miasta dostawy")
    private String city;
    private String state;
    @NotBlank(message = "Podaj kod pocztowy dostawy")
    private String postCode;
    @CreditCardNumber
    private String creditCardNo;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Wartość musi być w formacie MM/RR")
    private String creditCardExp;
    @Digits(integer = 3, fraction = 0, message = "Podaj poprawny numer kodu CVV")
    private String creditCardCvv;


}
