package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import ir.ut.ie.exceptions.InvalidUser;
import ir.ut.ie.utils.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("manager")
public class Manager extends User {

    public Manager(String username, String password, String email, String country, String city)
            throws InvalidAddress, InvalidUser {
        super(username, password, email, country, city);
    }

    @Override
    public UserRole getRole() {
        return UserRole.Manager;
    }
}
