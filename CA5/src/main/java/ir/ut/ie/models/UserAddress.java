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
    @JoinColumn
    User User;

    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;

    public UserAddress(User user, String country, String city) throws InvalidAddress {
        this.country = country;
        this.city = city;
        User = user;

        if(country.isEmpty() || city.isEmpty())
            throw new InvalidAddress();
    }
}
