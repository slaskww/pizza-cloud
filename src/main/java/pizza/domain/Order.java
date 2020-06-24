package pizza.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.*;

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
 *
 *  Chcąc zadeklarowac klasę jako encję JPA, musimy oznaczyć ją adnotacją @ Entity.
 *  Adnotacja @ Table została użyta, by wskazać JPA, by zapisał encję do tabeli Pizza_Order.  Bez tej adnotacji domyślnym działaniem byłoby zapisanie encji Order w tabeli
 *  o nazwie takiej nak nazwa klasy. Słowo ORDER jest zarezerwowane w SQL i jego użycie w kontekście nazwy tabeli może powodować problemy.
 *
 *  Kolejną rzeczą jest oznaczenie właściwości będącej identyfikatorem encji adnotacją @ Id
 *  Adnotacja @ GeneratedValue z atrybutem AUTO spowoduje, że baza danych zostanie użyta do automatycznego wygenerowania wartości identyfikatora.
 *
 *  Adnotacja @ ManyToMany deklaruje związek między obiektem Order a listą obiektów Pizza.
 *  Taka relacja wiele-do-wiele oznacza, że obiekt Order może być powiązany z wieloma obiektami Pizza. Obiekt Pizza możebyć natomiast powiązany z wieloma obiektami Order
 *  Atrybut cascade = CascadeType.REMOVE spowoduje kaskadowe usunięcie obiektów Pizza przypisanych do zamówienia po usunięciu obiektu Order.
 *  Adnotacja @JoinTable(name = "Pizza_Order_Design") spowoduje utworzenie pomocniczej tabeli Pizza_Order_Design, której wierszami będą  "order_id" jako klucz obcy  z encji Pizza_Order
 *  oraz  design_id jako klucz obcy  z encji Pizza.
 *                  joinColumns = @JoinColumn(name = "order_id"),
 *                  inverseJoinColumns = @JoinColumn(name = "design_id")
 *
 *
 *  Adnotacja @ PrePersist sprawi, że metoda zostanie uruchomiona, a w konsekwencji przypisana zostanie wartość właściwości orderedAt, zanim nastąpi trwały zapis obiektu Order.
 *
 *  Przy właściwości designs użyliśmy adotacji @JsonManagedReference.
 *  Wykorzystujemy ją w kontekście serializacji encji z dwukierunkowymi relacjami, by uniknąc problemów z niekończącą się rekurencją (infinite recursion).
 *  W ten sposób w pobranej/serializowanej encji Order, jej właściwość designs będzie zawierała kolekcję encji Pizza, ale każda z encji Pizza nie będzie już zawierała właściwości orders,
 *  wynikającej z dwukierunkowej relacji. W tym celu w klasie Pizza, nad właściwością orders umieszczamy adnotację @JsonBackReference.
 */

@Data
@Entity
@Table(name = "Pizza_Order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinTable(name = "Pizza_Order_Design",
                joinColumns = @JoinColumn(name = "order_id"),
                inverseJoinColumns = @JoinColumn(name = "design_id")
                )
   // @JsonManagedReference
    private List<Pizza> designs = new ArrayList<>();

    public void addDesign(Pizza pizza){
        this.designs.add(pizza);
        pizza.getOrders().add(this);
    }

    public void removeDesign(Pizza pizza){
        this.designs.remove(pizza);
        pizza.getOrders().remove(this);
    }

    private Date orderedAt;

    @ManyToOne
    private User user;

    public void fillWithUserData(){
        this.name = user.getFullName();
        this.street = user.getStreet();
        this.city = user.getCity();
        this.state = user.getState();
        this.postCode = user.getZipCode();
    }

    @PrePersist
    void orderedAt(){
        this.orderedAt = new Date();
    }
}
