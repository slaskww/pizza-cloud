package pizza.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pizza.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
