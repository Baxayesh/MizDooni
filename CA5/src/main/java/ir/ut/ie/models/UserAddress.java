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
@Table(name = "USER_ADDRESSES")
public class UserAddress {

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
