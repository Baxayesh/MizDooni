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
    Restaurant Restaurant;

    @Column(nullable = false)
    String Country;
    @Column(nullable = false)
    String City;
    @Column(nullable = false)
    String Street;



    public RestaurantAddress(Restaurant restaurant, String country, String city, String street) throws InvalidAddress {
        this.Country = country;
        this.City = city;
        this.Street = street;
        Restaurant = restaurant;
        Validate();
    }

    void Validate() throws InvalidAddress {
        if (
                Country == null ||
                Country.isEmpty() ||
                City == null ||
                City.isEmpty() ||
                Street == null ||
                Street.isEmpty()
        ) {
            throw new InvalidAddress();
        }
    }

    @Override
    public String toString() {
        return "%s, %s, %s".formatted(Street, City, Country);
    }
}
