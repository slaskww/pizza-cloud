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
import pizza.domain.User;
import pizza.dto.RegistrationForm;
import pizza.repositories.jpa.UserRepository;

/**
 * Kontroler będzie odpowiedzialny za realizację zadania rejestracji nowego użytkownika.
 * Metoda processRegistration() obsługująca żądanie POST otrzyma w danych żądania obiekt RegistrationForm.
 * Jest to obiekt, który zostanie dodany jako atrybut widoku,  wypełniony danymi z formularza rejestracyjnego i odesłany w żądaniu POST.
 * Klasa ta reprezentuje obiekt transferowy (DTO), który posiada wszystkie niezbędne pola nowo zarejestrowanego użytkownika.
 * Posiada także metodę toUser() mapującą obiekt DTO na obiekt domeny. Klasę RegistrationForm tworzymy w dalszej kolejności.
 * Następnie wykorzystujemy repozytorium użytkownika by utwalić nowy obiekt User w bazie danych.
 */

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
   public RegistrationForm registerForm(){
        return new RegistrationForm();
    }

    @GetMapping
   public String registrationForm(Model model){
        return "registrationForm";
    }

    @PostMapping
   public String processRegistration(RegistrationForm registrationForm, Errors errors){

        if (errors.hasErrors()){
            log.info("błąd" + errors.getFieldErrors());
            return "registrationForm";
        }

        log.info(registrationForm.toString());
        User userToPersist = registrationForm.toUser(passwordEncoder);
        userRepository.save(userToPersist);

        return "redirect:/login";
    }
}
