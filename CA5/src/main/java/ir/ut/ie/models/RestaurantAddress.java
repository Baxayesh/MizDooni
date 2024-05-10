package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import jakarta.persistence.*;
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

    @Id
    @OneToOne
    @JoinColumn
    Restaurant Restaurant;

    @Column(nullable = false)
    String country;
    @Column(nullable = false)
    String city;
    @Column(nullable = false)
    String street;



    public RestaurantAddress(Restaurant restaurant, String country, String city, String street) throws InvalidAddress {
        this.country = country;
        this.city = city;
        this.street = street;
        Restaurant = restaurant;
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
