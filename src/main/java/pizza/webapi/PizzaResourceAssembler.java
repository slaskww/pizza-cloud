package pizza.webapi;

import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pizza.domain.Pizza;
import pizza.webapi.model.PizzaResource;

/**
 * Klasa komponentu asemblera zasobu. Jest ona wykorzystywana do konwersji listy obiektów Pizza na obiekt CollectionModel<PizzaResource>.
 *
 * List<Pizza> recents;
 * CollectionModel<PizzaResource> recentResources = new PizzaResourceAssembler().toCollectionModel(recents);
 *
 * Konstruktor domyślny klasy PizzaResourceAssembler informuje klasę nadrzędną (RepresentationModelAssemblerSupport), że dla ustalenia
 * bazowej ścieżki dostępu dla adresów URL w łączach Link powstających podczas tworzenia PizzaResource, zostanie wykorzystany obiekt DesignPizzaRestController.
 *
 * Medoda instantiateModel() towrzy obiekt PizzaResource na podstawie obiektu Pizza.
 * Metoda toModel() nakazuje utworzenie obiektu PizzaResource na podstawie obiektu Pizza (wewnętrznie wywołuje metodę instantiateModel), a następnie dodaje do niego łącze self (Link) Z adresem URL pochodzącym z właściwości id obiektu Pizza.
 *
 * Metoda toCollectionModel() otrzymuje listę obiektów Pizza, przeprowadza iterację po obiektach Pizza i dla każdego z nich wywołuje metodę toModel() konwertując instancę Pizza n aobiekt PizzaResource.
 * Następnie tworzy kolekcję obiektów PizzaResource w postaci CollectionModel<PizzaResource>.
 */

public class PizzaResourceAssembler extends RepresentationModelAssemblerSupport<Pizza, PizzaResource> {

    public PizzaResourceAssembler() {
        super(DesignPizzaRestController.class, PizzaResource.class);
    }

    @Override
    protected PizzaResource instantiateModel(Pizza entity) {
        return new PizzaResource(entity);
    }

    @Override
    public PizzaResource toModel(Pizza entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    public CollectionModel<PizzaResource> toCollectionModel(Iterable<? extends Pizza> entities) {
        return super.toCollectionModel(entities);
    }
}
