package pizza.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class Order {

    private String id;

    @NotBlank(message = "Podaj imię i nazwisko")
    private String name;
    @NotBlank(message = "Podaj ulicę dostawy")
    private String street;
    @NotBlank(message = "Podaj nazwę miasta dostawy")
    private String city;
    private String state;
    @NotBlank(message = "Podaj kod pocztowy dostawy")
    private String postCode;
    @CreditCardNumber
    private String creditCardNo;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Wartość musi być w formacie MM/RR")
    private String creditCardExp;
    @Digits(integer = 3, fraction = 0, message = "Podaj poprawny numer kodu CVV")
    private String creditCardCvv;


}
