package pizza.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizza.domain.Pizza;

/**
 * W przypadku implementacji interfejsów z JDBC należało zadbać o to, by zadeklarować metody realizujące operacje CRUD.
 * W przypadku JPA nie ma potrzeby samodzielnej implementacji metod.
 * Rozszerzając nasz interfejs o sparametryzowany interfejs JpaRepository otrzymujemy gotowy zestaw metod ogólnego przeznaczenia trwałego magazynu danych dla encji.
 * Po uruchomieniu aplikacji Spring Data JPA samodzielnie generuje potrzebne implementacje.
 *
 */

@Repository
public interface JpaPizzaRepository extends JpaRepository<Pizza, Long> {
}
