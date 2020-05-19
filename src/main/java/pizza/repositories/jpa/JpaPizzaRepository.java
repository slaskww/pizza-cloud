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
 * Jeśli wymagamy od repozytorium, aby wykonywał zapytania unikalne dla danej domeny, możemy posłużyć się deklaracjami metod w oparciu o kluczowe elementy sygnatury metody, tj.
 *  - czasownik w języku ang. (find/read/get/count)
 *  - opcjonalny podmiot (Pizza)
 *  - słowo 'By'
 *  - predykat
 *
 * Jeśli specyfika zapytania wymaga użycia bardziej skomplikowanej nazwy, możemy posłużyć się adnotacją @ Query, ktrej atrybutem jest zapytanie pobierające informację z bazy danych.
 * Takiej metodzie możemy nadac dowolną nazwę. Przy pomocy tej adnotacji możemy wykonać dużo bardziej skomplikowane zapytania przy pomocy języka JPQL lub natywnego języka SQL
 * (w tym celu należy ustawić wartość atrybutu nativeQuery = true).
 */

@Repository
public interface JpaPizzaRepository extends JpaRepository<Pizza, Long> {
}
