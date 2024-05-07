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
@Table(name = "USER_ADDRESSES")
public class UserAddress {

    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;

    public UserAddress(String country, String city) throws InvalidAddress {
        this.country = country;
        this.city = city;

        if(country.isEmpty() || city.isEmpty())
            throw new InvalidAddress();
    }
}
