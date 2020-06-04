package pizza.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Bean egzemplarza przygotowanej wcześniej implementacji UserDetailsService wykorzystujemy do skonfigurowania usługi szczegółowych imformacji o użytkowniku,
 * dostarczając go metodzie userDetailsService().
 * Dodatkowo konfigurujemy implementację szyfrowania, aby hasło zapisane w bazie danych mogło zostać zaszyfrowane.
 * Metoda zwracająca instancję PasswordEncoder jest oznaczona adnotacją @ Bean, zostanie więc użyta do stworzenia beana BCryptPasswordEncoder w kontekście
 * aplikacji Stringa. Wywołanie metody encoder() w metodzie configure() zostanie przechwycone i zostanie zwrócony egzemplarz komponentu beana BCryptPasswordEncoder
 * z kontekstu aplikacji Springa.
 *
 * Klasa konfigurująca mechanizm zabezpieczeń webowych w naszej aplikacji odpowiada za uwierzytelnienie użytkownika oraz jego autoryzacje.
 * W naszej aplikacji obowiązują pewne reguły wedle których niektóre zasoby, tj. strona główna, strona rejestracji nowego użytkownika i strona logowania
 * powinny być dostępne również dla nieuwierzytelnionych użytkowników aplikacji. Konfigurację mechanizmu zabezpieczeń na poziomie sieci dostarczamy w metodzie
 * configure() przyjmującej jako parametr obiekt HttpSecurity. HttpSecurity umożliwia skonfigurowanie:
 *  -wymagań, które musza być spełnione, aby żądanie HTTP zostało wykonane (np. przy użyciu metody access("SpEL") i reguł opartych na Spring Expression Language)
 *  -niestandardowej strony logowania (metoda formLogin() i loginPage() ze ścieżką dostępu do strony logowania,
 *      utworzeniem kontrolera obsługującego żądania HTTP w tej ścieżce zajmie się klasa WebConfig)
 *  -możliwości wylogowania się użytkownika z aplikacji (metody logout().logoutSuccessUrl("/login") powodują powstanie filtra przechwytującego żądanie POST  do '/logout'
 *      i usunięcie bieżącej sesji użytkownika. Aby żądanie mogło być wywołane, musimy umieścić w widokach aplikacji formularz wylogowania i przycisk. )
 *  -zabezpieczenia przed atakami typu cross-site request forgery (Spring Security ma wbudowany mechanizm zabezpieczenia przed atakami csrf i jest on automatycznie włączany do aplikacji
 *      Jedyne, co trzeba zrobić to dołączyć do formularzy widoków pole '_csrf' przeznaczone dla tokena CSRF.
 *      Polecenie w szablonie Thymeleaf: <input type="hidden" name="_csrf" th:value="${_csrf.token}"/> wygeneruje token i umieści go w atrybucie żądania)
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Qualifier("userRepositoryUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/design", "/orders", "/orders/**").hasRole("USER")
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/design", true)
                .and()
                .logout().logoutSuccessUrl("/login")
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }
}
