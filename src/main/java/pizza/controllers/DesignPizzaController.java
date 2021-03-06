package pizza.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pizza.domain.Ingredient;
import pizza.domain.Order;
import pizza.domain.Pizza;
import pizza.repositories.jdbc.JdbcIngredientRepository;
import pizza.repositories.jdbc.JdbcPizzaRepository;
import pizza.repositories.jpa.JpaIngredientRepository;
import pizza.repositories.jpa.JpaPizzaRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Koncepcja klasay kontrolera (obsługującej żądania HTTP) jest 'centrum' frameworka internetowego Spring MVC
 * Adnotacja Controller nad klasą oznacza tę klasę jako komponent. Mechanizm skanowania komponentów Springa odnajdzie tę klase i utworzy
 * egzemplarz HomeController jako komponent bean w kontekście Springa
 * Adnotacja @RequestMapping określa, że po wygenerowaniu żądania HTTP Get do ścieżki "/design", to metod home obsłuży to żądanie.
 * Metoda showDesignForm() zwraca łańcuch String, który interpretowany jest jako logiczna nazwa widoku (domnyślnie /templates/nazwa.html).
 */

@Controller
@RequestMapping("/design")
@Slf4j
@SessionAttributes("order")
public class DesignPizzaController {

    private final JpaIngredientRepository ingredientRepository;
    private final JpaPizzaRepository pizzaRepository;

    public DesignPizzaController(JpaIngredientRepository ingredientRepository, JpaPizzaRepository pizzaRepository) {
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @ModelAttribute(name = "design")
    public Pizza pizza(){
        return new Pizza();
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @GetMapping
    public String showDesignForm(Model model, Order order){
        log.info("showDesignForm/pizzas:" + order.getDesigns().size() + "ordername:" + order.getName());


       List<Ingredient> ingredients = new ArrayList<>();
       ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));

       Ingredient.Type[] types = Ingredient.Type.values();
       for (Ingredient.Type type : types){
           model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
       }
        return "design";
    }
    @PostMapping
    public String processDesignForm(@Valid @ModelAttribute("design") Pizza design, Errors errors, Model model, Order order){
        if(errors.hasErrors()){
            log.info("błąd" + errors.getFieldErrors());
            log.info("pizza contains: " + design.toString());
            List<Ingredient> ingredients = new ArrayList<>();
            ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));

            Ingredient.Type[] types = Ingredient.Type.values();
            for (Ingredient.Type type : types){
                model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
            }
            return "design";
        }

        log.info("Przetwarzanie projektu pizzy " + design.getName() + " z liczbą składników: " + design.getIngredients().size());
       Pizza saved = pizzaRepository.save(design);
       order.addDesign(saved);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type){
       return ingredients.stream()
               .filter(ingredient -> ingredient.getType() == type)
               .collect(Collectors.toList());
    }

    private  List<Ingredient> fillWithIngredients(){
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
        return ingredients;
    }
}
