package pizza.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @GetMapping
    String registationForm(){
        return "registrationForm";
    }

    @PostMapping
    String processRegistration(){
        return "redirect:/login";
    }
}
