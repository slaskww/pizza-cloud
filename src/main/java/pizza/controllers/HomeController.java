package pizza.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Koncepcja klasay kontrolera (obsługującej żądania HTTP) jest 'centrum' frameworka internetowego Spring MVC
 *
 * Adnotacja Controller nad klasą oznacza tę klasę jako komponent. Mechanizm skanowania komponentów Springa odnajdzie tę klase i utworzy
 * egzemplarz HomeController jako komponent bean w kontekście Springa
 *
 * Adnotacja GetMapping określa, że po wygenerowaniu żądania HTTP Get do ścieżki "/", to metod home obsłuży to żądanie.
 * Metoda home() zwraca łańcuch String, który interpretowany jest jako logiczna nazwa widoku (domnyślnie /templates/nazwa.html).
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(){
        return "home";
    }
}
