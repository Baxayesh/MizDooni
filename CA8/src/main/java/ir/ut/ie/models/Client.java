package ir.ut.ie.models;

import ir.ut.ie.utils.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("client")
public class Client extends User {

    public Client(String username, String password, String email, String country, String city) {
        super(username, password, email, country, city);
    }

    @Override
    public UserRole getRole() {
        return UserRole.Client;
    }
}
