package pizza.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pizza.dto.RegistrationForm;
import pizza.repositories.jpa.UserRepository;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(name = "registerForm")
    RegistrationForm registerForm(){
        return new RegistrationForm();
    }

    @GetMapping
    String registationForm(Model model){
        return "registrationForm";
    }

    @PostMapping
    String processRegistration(RegistrationForm registrationForm, Errors errors){

        if (errors.hasErrors()){
            log.info("błąd" + errors.getFieldErrors());
            return "registrationForm";
        }

        log.info(registrationForm.toString());
        userRepository.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
