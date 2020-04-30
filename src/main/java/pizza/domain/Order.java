package pizza.domain;

import lombok.Data;

@Data
public class Order {

    private String id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String postCode;
    private String creditCardNo;
    private String creditCardExp;
    private String creditCardCvv;


}
