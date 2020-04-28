package pizza;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  Adnotacja @ RunWith(SpringRunner.class) jest adnotacją JUnit, którqa wskazuje mechanizm preznaczony do wykonania testu przez JUnit.
 *  JUnit wykorzystuje mechanizm (SpringRunner czyli SpringJUnit4ClassRunner) wykonywania testów dostarczony przez Spring.
 *  Dostarcza kontekst aplikacji Sprinka potrzebny do testów.
 *
 *  Adnotacja @ SpringBootTest jest odpowiednikiem SpringApplication.run() w metodzie main() i nakazuje wykonanie testu z możliwościami Spring Boota
 */

@RunWith(SpringRunner.class)
@SpringBootTest
class PizzaCloudApplicationTests {

	@Test
	void contextLoads() {
	}

}
