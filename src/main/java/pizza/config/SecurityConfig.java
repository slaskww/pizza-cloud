package pizza.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                .antMatchers("/design", "/orders").hasRole("USER")
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design")
        ;
    }
}
