package pizza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Klasa konfigurująca Spring MVC implementuje interfejs WebMvcConfigurer, kóry dostarcza domyślną implementację dla wszystkich jego metod.
 * Dlatego też, nadpisujemy te metody, których sposób działania chcielibyśmy zmienić.
 *
 * Metoda addViewControllers() służy do rejestracji nowych kontrolerów widoku.
 * W ten sposób możemy zastąpić tradycyjny kontroler widoku HomeController.
 * Dla większoścli kontrolerów wzorzec klasy kontrolera będzie lepszym rozwiązaniem.
 * Jednak w przypadku, gdy kontroler jest prosty, nie wypełnia danych modelu ani nie przetwarza danych wejściowych, można skorzystać z rejestratora.
 *
 * Kontroler obsługujący stronę logowania jest na tyle prosty, że skorzystanie z rejestratora wystarczy.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }
}
