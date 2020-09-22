package pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.stereotype.Repository;

/**
 * @ SpringBootApplication zawiera:
 *  	@ SpringBootConfiguration  	- określa klasę jako konfiguracyjną
 *  	@ ComponentScan 			- adnotacja włącza skanowanie komponentu, umożliwia deklarację innych klas z adnot. Controller, Service, Component, Repository
 *  	@ EnableAutoConfiguration	- włącza konfigurację automatyczną Spring Boot
 *
 *  metoda main() uruchamia statyczną metodę run() klasy SpringApplication, która przygotowuje aplikację i tworzy kontekst aplikacji Springa
 */

@Repository
//@SpringBootApplication
@SpringBootApplication()
		//exclude = {SecurityAutoConfiguration.class,
		//UserDetailsServiceAutoConfiguration.class})
public class PizzaCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaCloudApplication.class, args);
	}

}
