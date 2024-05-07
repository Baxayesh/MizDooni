package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import ir.ut.ie.exceptions.InvalidUser;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
public abstract class User extends EntityModel<String> {

    private String username;
    private String password;
    private String email;

    private UserAddress address;

    public User(String username, String password, String email, String country, String city) throws InvalidAddress, InvalidUser {
        super(username);

        this.username = username;
        this.password = password;
        this.email = email;
        this.address = new UserAddress(country, city);
        ValidateUser();
    }




    void ValidateUser()
            throws InvalidUser, InvalidAddress {

        if (username.contains(" ") || username.contains(";") || !Pattern.matches("^[a-zA-Z0-9_]*$", username)){
            throw new InvalidUser();
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)){
            throw new InvalidUser();
        }

    }

}