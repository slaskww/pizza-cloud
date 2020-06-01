package pizza.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import pizza.domain.Order;
import pizza.domain.User;
import pizza.repositories.jpa.JpaOrderRepository;

import javax.validation.Valid;



@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final JpaOrderRepository orderRepository;

    public OrderController(JpaOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String showOrderForm(Model model){
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrderForm(@Valid Order order, Errors errors, SessionStatus sessionStatus,  @AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "orderForm";
        }

        order.setUser(user);
        orderRepository.save(order);
        sessionStatus.isComplete();
        return "redirect:/";
    }
}
