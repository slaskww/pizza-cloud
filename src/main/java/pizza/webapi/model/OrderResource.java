package pizza.webapi.model;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import pizza.domain.Order;
import pizza.domain.User;
import pizza.webapi.PizzaResourceAssembler;
import java.util.Date;

/**
 * Klasa OrderResource jest klasą narzędziową, która konwertuje obiekt Order na obiekt OrderResource. Nowy obiekt tym się będzie różnił of Order, że pozwoli
 * na przekazywanie łączy (Link). Dzięki temu, że klasa rozszerza  RepresentationModel, dziedziczy listę obiektów Link (łączy), oraz metod do zarządzania tą listą.
 * W obiekcie OrderResouce pominęliśmy właściwość id z obiektu Order. Zależy nam bowiem na tym, by nie udostępniać w API identyfikatorów związanych z bazą danych.
 * Takim identyfikatorem dla klienta API będzie łącze self zasobu.
 * Teraz w OrderRestControllerze obiekt EntityModel<Order> będziemy mogli zastąpić obiektem PizzaResource.
 *
 * Żeby można było łatwo przekonwertować listę obiektów Order na obiekt CollectionModel<OrderResource> musimy posłużyć się komponentem asemblera zasobu. W tym celu tworzymy
 * klase OrderResourceAssembler, która rozszerza RepresentationModelAssemblerSupport<Order, OrderResource>
 *
 *  Adnotacja Relation służy do konfiguracji relacji użytej przy osadzaniu obiektów IngredientResource jako hiperłączy w odpowiedziach JSON (format HAL).
 *  Domyślnie pole w JSON dla relacji z pojedyńczym zasobem przyjmuje nazwę klasy IngredientResource. Atrybut value umożliwia zmianę tej nazwy.
 *  Analogicznie nazwa pola w JSON dla relacji z kolekcją zasobów to IngredientResourceList. Atrybut collectionRelation umożliwia zmianę tej nazwy.
 *  Adnotacja ta zapobiega przyszłym problemom użytkowników API (wykorzystujących na stałe zdefiniowaną nazwę IngredientResourceList), jakie mogłyby wyniknąć ze zmiany nazwy klasy zasobu w ramach faktoryzacji.
 */

@Getter
@Relation(value = "order", collectionRelation = "orders")
public class OrderResource extends RepresentationModel<OrderResource> {


    private String name;
    private String street;
    private String city;
    private String state;
    private String postCode;
    private String creditCardNo;
    private String creditCardExp;
    private String creditCardCvv;
    private CollectionModel<PizzaResource> designs;
    private Date orderedAt;

    @Getter(AccessLevel.NONE)
    private User user;

    public OrderResource(Order order) {
        this.name = order.getName();
        this.street = order.getStreet();
        this.city = order.getCity();
        this.state = order.getState();
        this.postCode = order.getPostCode();
        this.creditCardCvv = order.getCreditCardCvv();
        this.creditCardNo = order.getCreditCardNo();
        this.creditCardExp = order.getCreditCardExp();
        this.designs = new PizzaResourceAssembler().toCollectionModel(order.getDesigns());
        this.orderedAt = order.getOrderedAt();
        this.user = order.getUser();
    }
}
