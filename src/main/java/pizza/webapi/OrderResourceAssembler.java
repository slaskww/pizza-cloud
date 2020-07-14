package pizza.webapi;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pizza.domain.Order;
import pizza.webapi.model.OrderResource;

/**
 * Klasa komponentu asemblera zasobu. Jest ona wykorzystywana do konwersji listy obiektów Order na obiekt CollectionModel<OrderResource>.
 *
 * List<Order> orders;
 * CollectionModel<OrderResource> recentResources = new IngredientResourceAssembler().toCollectionModel(orders);
 *
 * Konstruktor domyślny klasy OrderResourceAssembler informuje klasę nadrzędną (RepresentationModelAssemblerSupport), że dla ustalenia
 * bazowej ścieżki dostępu dla adresów URL w łączach Link powstających podczas tworzenia OrderResource, zostanie wykorzystany obiekt OrderRestController.
 *
 * Medoda instantiateModel() towrzy obiekt OrderResource na podstawie obiektu Order.
 * Metoda toModel() nakazuje utworzenie obiektu OrderResource na podstawie obiektu Order (wewnętrznie wywołuje metodę instantiateModel), a następnie dodaje do niego łącze self (Link) Z adresem URL pochodzącym z właściwości id obiektu Order.
 *
 * Metoda toCollectionModel() otrzymuje listę obiektów Order, przeprowadza iterację po obiektach Order i dla każdego z nich wywołuje metodę toModel() konwertując instancę Order na obiekt OrderResource.
 * Następnie tworzy kolekcję obiektów OrderResource w postaci CollectionModel<OrderResource>.
 */

public class OrderResourceAssembler extends RepresentationModelAssemblerSupport<Order, OrderResource> {

    public OrderResourceAssembler() {
        super(OrderRestController.class, OrderResource.class);
    }

    @Override
    public OrderResource toModel(Order entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    public CollectionModel<OrderResource> toCollectionModel(Iterable<? extends Order> entities) {
        return super.toCollectionModel(entities);
    }

    @Override
    protected OrderResource instantiateModel(Order entity) {
        return new OrderResource(entity);
    }
}
