package pizza.props.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Klasa komponentu bean, którego jedynym zadaniem jest przechowywanie danych konfiguracyjnych.
 * Takie podejście pozwala przenieść szczegóły konfiguracji właściwści kontrolera poza klasę kontrolera
 * i uczynić jego kod czystszym.
 * Klasa OrderProps przechowuje właściwość pageSize, która w poprzedniej wersji należała do klasy OrderController.
 * Również walidacja dla konfigurowanej właściwości została przeniesiona do tej klasy.
 *
 * Kluczowe w tej klasie jest użycie adnotacji @ ConfigurationProperties, która zapewnia obsługę wstrzykiwania
 * właściwości konfiguracyjnych.
 * Zmiany wartości pageSize można następnie dokonać w pliku application.properties (pizza.orders.page-size= 12)
 *
 * Komponent OrderProps jest następnie wstrzykiwany i wykorzystywany w klasie OrderController.
 */

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "pizza.orders")
public class OrderProps {

    @Max(value = 25, message = "Wartość powinna mieścić się w zakresie 5 - 25")
    @Min(value = 5, message = "Wartość powinna mieścić się w zakresie 5 - 25")
    private int pageSize = 10;
}
