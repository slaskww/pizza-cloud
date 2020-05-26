package pizza.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pizza.domain.User;
import pizza.repositories.jpa.UserRepository;

/**
 * Mając implementację interfejsu UserDetails oraz repozytorium UserRepository, możemy zaimplementować interfejs serwisu userdetails.
 * Nasz Serwis implementuje inerfejs UserDetailsService ze Spring Security i nadpisuje metode loadUserByUsername().
 * Metoda zwraca typ UserDetails, więc możemy zwrócić instancję naszej klasy User, która implementuje interfejs UserDetails.
 * Metoda nie może zwrócić wartości null, stąd jeśli wynikiem wywołania metody z repozytorium będzie wartość null, metoda zgłosi wyjątek
 * UsernameNotFoundException.
 * Nasz serwis w postaci komponentu bean zostanie wstrzyknięty w klasie SecurityConfig
 *
 */

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepository.findByUsername(username);
        if (user != null){
            return user;
        }
        throw new UsernameNotFoundException("Użytkownik " + username + " nie istnieje w bazie");

    }
}
