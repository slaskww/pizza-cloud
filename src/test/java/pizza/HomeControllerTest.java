package pizza;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Spring Boot dostarcza dnotację @ WebMvcTest przeznaczoną do testów w kontekście aplikacji Spring MVC.
 * Parametr odpowiadający klasie kontrolera powoduje, że klasa HomeController zostaje zarejestrowania w Spring MVC,
 * co umożliwia wykonywanie do niej żądań HTTP.
 *
 * W teście wykorzystujemy mechanizm imitacji MVC. Obiekt MockMvc wykonuje żądanie do katalogu głownego '/' ściezki dostępu.
 * Ządanie to powinno kolejno:
 *      wywołać odpowiedź OK (kod stanu HTTP 200),
 *      metoda obsługująca ządanie powinna zwrócić widok o nazwie 'home',
 *      wygenerowanany wynik powinien zawierać ciąg 'Witaj w'
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Witaj w")));
    }
}
