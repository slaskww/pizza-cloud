package pizza.domain;

import lombok.Data;

import java.util.List;

@Data
public class Pizza {

    private String id;
    private String name;
    private List<String> ingredients;
}
