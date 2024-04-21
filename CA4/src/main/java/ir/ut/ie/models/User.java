package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import ir.ut.ie.exceptions.InvalidUser;
import lombok.Getter;
import lombok.Setter;
import ir.ut.ie.utils.UserRole;

import java.util.regex.Pattern;
@Getter
@Setter
public class User extends EntityModel<String> {

    private String role;
    private String username;
    private String password;
    private String email;
    private Address userAddress;

    public User(String username, String role, String password, String email, Address address) {
        super(username);
        //Role = role;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        userAddress = address;

    }




    public static void ValidateUser(String username, String role, String password, String email, Address address)
            throws InvalidUser, InvalidAddress {

        if (!role.equals("client") && !role.equals("manager")){
            throw new InvalidUser();
        }
        if (username.contains(" ") || username.contains(";") || !Pattern.matches("^[a-zA-Z0-9_]*$", username)){
            throw new InvalidUser();
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)){
            throw new InvalidUser();
        }
        if (address.country == null || address.country.isEmpty() || address.city == null || address.city.isEmpty()) {
            throw new InvalidAddress();
        }

    }

    public boolean RoleIs(UserRole desiredRole) {
        return role.equalsIgnoreCase(desiredRole.toString());
    }


    public record Address(String country, String city){}
}