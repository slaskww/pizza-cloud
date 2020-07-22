package pizza.webapi.clientmock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pizza.domain.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/client/ingredients")
public class IngredientRestAPIConsumerController {

    private final IngredientRestAPIConsumer ingredientRestAPIConsumer;

    public IngredientRestAPIConsumerController(IngredientRestAPIConsumer ingredientRestAPIConsumer) {
        this.ingredientRestAPIConsumer = ingredientRestAPIConsumer;
    }

    @GetMapping
    public String showClientPanel(){
        return  "clientPanel";
    }

    @PostMapping
    public String getAllIngredients(Model model){

        log.info("before fetching data from ingredientRestAPIConsumer");
        List<Ingredient> ingredientList = new ArrayList<>(ingredientRestAPIConsumer.getAllIngredients());
        model.addAttribute("ingredientList", ingredientList);
        log.info("before returning view name");
        return "clientPanel";
    }
}
