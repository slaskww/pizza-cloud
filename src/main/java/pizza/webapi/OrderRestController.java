package pizza.webapi;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizza.domain.Order;
import pizza.props.config.OrderProps;
import pizza.repositories.jpa.JpaOrderRepository;



@RestController
@RequestMapping(path = "/order", produces = "application/json")
public class OrderRestController {

    private final JpaOrderRepository jpaOrderRepository;
    private final OrderProps props;

    public OrderRestController(JpaOrderRepository jpaOrderRepository, OrderProps orderProps) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.props = orderProps;
    }

    @GetMapping("/recent")
   public Iterable<Order> getOrder(){
        PageRequest page = PageRequest.of(0, 1,Sort.by("orderedAt").descending());
       return jpaOrderRepository.findAll(page);
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
