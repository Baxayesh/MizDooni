package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantAddress {

    String country;
    String city;
    String street;

    public RestaurantAddress(String country, String city, String street) throws InvalidAddress {
        this.country = country;
        this.city = city;
        this.street = street;
        Validate();
    }

    void Validate() throws InvalidAddress {
        if (
                country == null ||
                country.isEmpty() ||
                city == null ||
                city.isEmpty() ||
                street == null ||
                street.isEmpty()
        ) {
            throw new InvalidAddress();
        }
    }

    @Override
    public String toString() {
        return "%s, %s, %s".formatted(street, city, country);
    }
}
