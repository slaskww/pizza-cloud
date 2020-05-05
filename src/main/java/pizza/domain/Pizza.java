package pizza.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Pizza {

    private String id;
    @NotNull
    @Size(min = 5, message = "Nazwa powinna zawierać co najmniej 5 znaków")
    private String name;
    @Size(min = 1, message = "Wybierz co najmniej jeden składnik")
    private List<String> ingredients;
}
