package pizza.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

/**
 * Klasa User reprezentuje nowo zarejestrowanego użytkownika.
 * Klasa ta implementuje interfejs UserDetails pochodzącą ze Spring Security, kóry dostarczy frameworkowi pewne informacje
 * o użytkowniku (nadpisana metoda getAutorities() dostarcza info o uprawnieniach usera, pozostałe metody dostarczają informacje o tym, czy konto jest aktywne, a także o loginie i hasle).
 * Dzięki temu, że nasz User implementuje interfejs UserDetails, możemy jego egzemplarz dostarczyć do klasy UserRepositoryUserDetailsService, która z kolei
 * zostanie wykorzystana w konfiguracji wewnątrz klasy SecurityConfig
 *
 */

@Data
@Entity
//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    @JsonIgnore
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;

    public User(String username, String password, String fullName, String street, String city, String state, String zipCode, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
