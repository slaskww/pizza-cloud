package pizza.webapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import pizza.domain.Order;
import pizza.domain.User;
import pizza.props.config.OrderProps;
import pizza.repositories.jpa.JpaOrderRepository;
import pizza.repositories.jpa.JpaPizzaRepository;
import pizza.repositories.jpa.UserRepository;
import pizza.webapi.model.OrderResource;
import java.util.List;
import java.util.Optional;

/**
 * Adnotacja RepositoryRestController oferowana przez Spring Data REST sprawia, że mapowanie ścieżki dostępu do zasobów kontrolera nastąpi wg. konfiguracji ścieżki bazowej
 * punktów końcowych Spring Data REST (skonfigurowanej w application.properties). Adnotacja ta nie zawiera w sobie adnotacji @ResponseBody, więc należy pamiętać o jej umieszczeniu
 * nad metodą zwracającą odpowiedź, lub opakować odpowiedź w obiekt ResponseEntity<>.
 * Spring Data REST udostępnia REST API 'out of the box', bazując na inerfejsach repozytoriów. Jeśli więc zależy nam na udostępnieniu punktów końcowych przeprowadzających operacje CRUD,
 * jest on w stanie w zupełności zastąpić kontrolery restowe.
 * W naszym przypadku Springowe Data REST endpointy zostały wzbogacone o ścieźkę do ostatnio dodanych obiektów Order (/api/orders/recent). W prawdzie Spring Data REST umożliwia wykonanie żądania
 * do zasobu z opcjonalnymi parametrami (page,size,sort), np: "/api/orders/?sort=createdAd,desc&page=0&size=12", ale powoduje to zaśmiecanie kodu i wymusza na kliencie definiowanie parametrów żądania 'na stale'.
 * Nam zależałoby na tym, by klient odszukał zwięzły adres URL z ostatnio dodanymi zamówieniami (/api/orders/recent) na liście łączy Spring Data w punkcie końcowym /api/orders.
 * Domyślnie własne endpointy nie zostają dodane i zwrócone razem z endpointami Spring Data. By takie łącza dodać do dostarczanej automatycznie listy,
 * należy posłużyć się komponentem procesora zasobu. Służy on do przeprowadzania operacji na zasobach zanim zostana one zwrócone przez API. Taki komponent bean utworzyliśmy w klasie RestConfig.
 */

@Slf4j
@RepositoryRestController
public class OrderRestController {

    private final JpaOrderRepository jpaOrderRepository;
    private final UserRepository userRepository;
    private final JpaPizzaRepository jpaPizzaRepository;
    private final OrderProps props;

    public OrderRestController(JpaOrderRepository jpaOrderRepository, UserRepository userRepository, JpaPizzaRepository jpaPizzaRepository, OrderProps props) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.userRepository = userRepository;
        this.jpaPizzaRepository = jpaPizzaRepository;
        this.props = props;
    }

   // @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("orders/recent")
    @ResponseBody
   public CollectionModel<OrderResource> getOrder(){
        PageRequest page = PageRequest.of(0, props.getPageSize(),Sort.by("orderedAt").descending());
        List<Order> orders = jpaOrderRepository.findAll(page).getContent();
        CollectionModel<OrderResource> rOrders = new OrderResourceAssembler().toCollectionModel(orders);
        return rOrders.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderRestController.class).getOrder()).withRel("recents"));
    }

    @GetMapping("orders/{orderId}")
    public ResponseEntity<OrderResource> getById(@PathVariable("orderId") Long orderId){

        Optional<Order> optOrder = jpaOrderRepository.findById(orderId);

        if(optOrder.isPresent()){
            return new ResponseEntity<>(new OrderResource(optOrder.get())
                    .add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder
                                    .methodOn(OrderRestController.class)
                                    .getById(orderId))
                            .withRel("order")), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


    @PostMapping(value = "orders/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order, @AuthenticationPrincipal UserDetails user){
        User user1 = userRepository.findById(1L).get();
        order.setUser(user1);
        log.info(user1.toString());

        order.getDesigns().stream().forEach(pizza -> jpaPizzaRepository.save(pizza));

        return jpaOrderRepository.save(order);
    }

    @DeleteMapping("orders/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId){
        try{
            jpaOrderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e){}
    }

    @PatchMapping(value = "orders/{orderId}", consumes = "application/json")
    public Order patchOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody Order patch){

        Order order = jpaOrderRepository.findById(orderId).get();
        if(patch.getName() != null){
            order.setName(patch.getName());
        }
        if(patch.getCity() != null){
            order.setCity(patch.getCity());
        }
        if(patch.getStreet() != null){
            order.setStreet(patch.getStreet());
        }
        if(patch.getPostCode() != null){
            order.setPostCode(patch.getPostCode());
        }
        if(patch.getState() != null){
            order.setState(patch.getState());
        }
        if(patch.getCreditCardCvv() != null){
            order.setCreditCardCvv(patch.getCreditCardCvv());
        }
        if(patch.getCreditCardNo() != null){
            order.setCreditCardNo(patch.getCreditCardNo());
        }
        if(patch.getCreditCardExp() != null){
            order.setCreditCardExp(patch.getCreditCardExp());
        }
       return jpaOrderRepository.save(order);
    }

}
