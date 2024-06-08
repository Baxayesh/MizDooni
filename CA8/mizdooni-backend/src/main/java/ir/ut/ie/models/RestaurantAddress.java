package ir.ut.ie.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "RESTAURANT_ADDRESSES")
public class RestaurantAddress implements Serializable {

    @Id
    @OneToOne
    Restaurant Restaurant;

    @Column(nullable = false)
    String Country;
    @Column(nullable = false)
    String City;
    @Column(nullable = false)
    String Street;

    public RestaurantAddress(Restaurant restaurant, String country, String city, String street) {
        this.Country = country;
        this.City = city;
        this.Street = street;
        Restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "%s, %s, %s".formatted(Street, City, Country);
    }
}
