package pizza.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pizza.domain.Ingredient;
import pizza.domain.Pizza;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/design")
@Slf4j
public class DesignPizzaController {

    @GetMapping
    public String showDesignForm(Model model){

        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("DW", "Wheat", Ingredient.Type.DOUGH),
                new Ingredient("DC", "Corn", Ingredient.Type.DOUGH),
                new Ingredient("CM", "Mozarella", Ingredient.Type.CHEESE),
                new Ingredient("CC", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("MC", "Chicken", Ingredient.Type.MEAT),
                new Ingredient("MF", "Salmon", Ingredient.Type.MEAT),
                new Ingredient("MB", "Beef", Ingredient.Type.MEAT),
                new Ingredient("VT", "Tomato", Ingredient.Type.VEGETABLE),
                new Ingredient("VB", "Basil", Ingredient.Type.VEGETABLE),
                new Ingredient("VP", "Jalapeno", Ingredient.Type.VEGETABLE),
                new Ingredient("VO", "Onion", Ingredient.Type.VEGETABLE),
                new Ingredient("VO", "Oregano", Ingredient.Type.VEGETABLE),
                new Ingredient("VM", "Mushrooms", Ingredient.Type.VEGETABLE),
                new Ingredient("SG", "Garlic sauce", Ingredient.Type.SAUCE),
                new Ingredient("ST", "Tomato sauce", Ingredient.Type.SAUCE),
                new Ingredient("SB", "Barbecue sauce", Ingredient.Type.SAUCE)
        );

       Ingredient.Type[] types = Ingredient.Type.values();
       for (Ingredient.Type type : types){
           model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
       }
        model.addAttribute("design", new Pizza());
        return "design";
    }
    @PostMapping
    public String processDesignForm(Pizza pizza){
        log.info("Przetwarzanie projektu pizzy " + pizza.getName() + "z liczbą składników: " + pizza.getIngredients().size());

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type){
       return ingredients.stream()
               .filter(ingredient -> ingredient.getType() == type)
               .collect(Collectors.toList());
    }
}
