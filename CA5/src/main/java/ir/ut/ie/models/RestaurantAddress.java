package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "RESTAURANT_ADDRESSES")
public class RestaurantAddress {

    @Column(nullable = false)
    String country;
    @Column(nullable = false)
    String city;
    @Column(nullable = false)
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
