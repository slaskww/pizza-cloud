package pizza.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
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
 * o użytkowniku (getAutorities() dostarcza info o uprawnieniach usera, inne - informacje o tym, czy konto jest aktywne, a także login i hasło).
 * Dzięki temu, że nasz User implementuje interfejs UserDetails, możemy jego egzemplarz dostarczyć do klasy UserRepositoryUserDetailsService, która z kolei
 * zostanie wykorzystana w konfiguracji wewnątrz klasy SecurityConfig
 *
 */

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;

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
