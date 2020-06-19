package pizza.webapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizza.domain.Pizza;
import pizza.repositories.jpa.JpaOrderRepository;
import pizza.repositories.jpa.JpaPizzaRepository;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignPizzaRestController {

    private  JpaPizzaRepository jpaPizzaRepository;
    private JpaOrderRepository jpaOrderRepository;

    public DesignPizzaRestController(JpaPizzaRepository jpaPizzaRepository, JpaOrderRepository jpaOrderRepository) {
        this.jpaPizzaRepository = jpaPizzaRepository;
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @GetMapping(path = "/recent")
   public Iterable<Pizza> recentPizzas(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return jpaPizzaRepository.findAll(page);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pizza> getById(@PathVariable("id") Long id){
        Optional<Pizza> optPizza = jpaPizzaRepository.findById(id);
        if(optPizza.isPresent()){
            return new ResponseEntity<>(optPizza.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/new", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza postPizza(@RequestBody Pizza pizza){
        return jpaPizzaRepository.save(pizza);
    }


   @DeleteMapping("/{orderId}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId){
     try{
         jpaOrderRepository.deleteById(orderId);
     } catch (EmptyResultDataAccessException e){}
    }
}
