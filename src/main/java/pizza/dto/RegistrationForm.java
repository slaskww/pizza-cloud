package pizza.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import pizza.domain.User;

/**
 * Klasa RegistrationForm reprezentuje obiekt transferowy (DTO), który posiada wszystkie niezbędne pola nowo zarejestrowanego użytkownika.
 * Posiada także metodę toUser() mapującą obiekt DTO na obiekt domeny, uprzednio kodując parametr password.
 */

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phone;

  public User toUser(PasswordEncoder passwordEncoder){
      return new User(username, passwordEncoder.encode(password), fullName, street, city, state, zipCode, phone);
  }
}
