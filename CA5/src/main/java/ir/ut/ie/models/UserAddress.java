package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
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
@Table(name = "USER_ADDRESSES")
public class UserAddress implements Serializable {

    @Id
    @OneToOne
    User User;

    @Column(nullable = false)
    private String Country;
    @Column(nullable = false)
    private String City;

    public UserAddress(User user, String country, String city) throws InvalidAddress {
        Country = country;
        City = city;
        User = user;

        if(country.isEmpty() || city.isEmpty())
            throw new InvalidAddress();
    }
}
