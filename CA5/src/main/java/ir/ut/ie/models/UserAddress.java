package ir.ut.ie.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAddress {


    String country;
    String city;
    User owner;

    public UserAddress(User owner, String country, String city) {
        this.country = country;
        this.city = city;
        this.owner = owner;
    }
}
