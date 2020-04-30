package pizza.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pizza.domain.Order;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/current")
    public String showOrderForm(Model model){
        model.addAttribute("order", new Order);
        return "orderForm";
    }
}
