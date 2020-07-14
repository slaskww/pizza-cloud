package pizza.webapi.model;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
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
 */

@Getter()
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
