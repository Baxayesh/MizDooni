package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import ir.ut.ie.exceptions.InvalidUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("client")
public class Client extends User {

    public Client(String username, String password, String email, String country, String city)
            throws InvalidAddress, InvalidUser {
        super(username, password, email, country, city);
    }
}
