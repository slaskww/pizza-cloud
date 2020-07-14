package pizza.props.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "pizza.pizzas")
public class PizzaProps {

    @Min(value = 5, message = "Wartość powinna mieścić się w zakresie 5 - 25")
    @Max(value = 25, message = "Wartość powinna mieścić się w zakresie 5 - 25")
    private int pageSize = 10;
}
