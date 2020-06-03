package pizza.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pizza.domain.User;

/**
 * Poza operacjami dostarczanymi przez rozszerzenie JpaRepository, definiujemy metodę findByUsername()
 * pozwalającą na pobranie obiektu przedstawiającego użytkownika w oparciu o nazwę użytkownika.
 * Metodę wykorzystamy w serwisie użytkownika.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
