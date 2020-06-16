package pizza.webapi;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pizza.domain.Pizza;
import pizza.repositories.jpa.JpaPizzaRepository;

@RestController
@RequestMapping(path = "/design")
@CrossOrigin(origins = "*")
public class DesignPizzaRestController {

    private  JpaPizzaRepository jpaPizzaRepository;

    public DesignPizzaRestController(JpaPizzaRepository jpaPizzaRepository) {
        this.jpaPizzaRepository = jpaPizzaRepository;
    }

    @GetMapping(path = "/recent")
    Iterable<Pizza> recentPizzas(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return jpaPizzaRepository.findAll(page);
    }
}
