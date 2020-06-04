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

/**
 * Metoda processOrder odpowiada za zapisanie zamówienia. Przed utrwaleniem obiektu w bazie danych, ustawiamy pole 'user' dla tego zamównienia.
 * Wiadomo, że zamówienie zostaje złożone przez zalogowanego użytkownika, więc danę tą pobieramy z obiektu kontekstowego Springa.
 * Istnieje kilka sposobów na ustalenie kim jest zalogowany użytkownik:
 *  - wstrzyknięcie obiektu Principal do metody processOrderForm(), wywołanie na nim metody getName(), pobranie nazwy użytkownika i przy pomocy UserReposotory pobranie Usera z bazy danych,
 *  - wstrzyknięcie obiektu Authentication do metody processOrderForm(), wywołanie na nim metody getPrincipal() i rzutowanie zwracanego obiektu typu Object na typ User
 *  - użycie SecurityContextHolder i getContext(), aby pobrać obiekt kontekstu bezpieczeństwa (SecurityContext) a z niego obiekt Authentication, i dalej wywołac na nim metodę getPrincipal() zwracającą obiekt typu Object dalej rzutowaną na typ User
 *      Wersja z użyciem SecurityContextHolder ma tę przewagę, że można jej użyyć w dowolnym fragmencie kodu aplikacji (nie tylko w metodach kontrolera)
*   - użycie adnotacji @ AuthenticationPrincipal dla obiektu User w metodzie processOrderForm(),
 */

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
