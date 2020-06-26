package pizza.webapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pizza.domain.Order;
import pizza.domain.User;
import pizza.props.config.OrderProps;
import pizza.repositories.jpa.JpaOrderRepository;
import pizza.repositories.jpa.JpaPizzaRepository;
import pizza.repositories.jpa.UserRepository;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/order", produces = {"application/json"})
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

    @GetMapping("/recent")
   public Iterable<Order> getOrder(){
        PageRequest page = PageRequest.of(0, props.getPageSize(),Sort.by("orderedAt").descending());
       return jpaOrderRepository.findAll(page);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getById(@PathVariable("orderId") Long orderId){

        Optional<Order> optOrder = jpaOrderRepository.findById(orderId);

        if(optOrder.isPresent()){
            return new ResponseEntity<>(optOrder.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order, @AuthenticationPrincipal UserDetails user){
        User user1 = userRepository.findById(1L).get();
        order.setUser(user1);
        log.info(user1.toString());

        order.getDesigns().stream().forEach(pizza -> jpaPizzaRepository.save(pizza));

        return jpaOrderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId){
        try{
            jpaOrderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e){}
    }

    @PatchMapping(value = "/{orderId}", consumes = "application/json")
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
