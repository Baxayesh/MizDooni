package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class UserAddress {

    private String country;
    private String city;

    public UserAddress(String country, String city) throws InvalidAddress {
        this.country = country;
        this.city = city;

        if(country.isEmpty() || city.isEmpty())
            throw new InvalidAddress();
    }
}
