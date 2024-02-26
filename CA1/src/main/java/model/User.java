package model;

import lombok.Getter;
import lombok.Setter;
import utils.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Setter
public class User extends EntityModel<String> {

    private UserRole Role;
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

    public boolean RoleIs(UserRole desiredRole) {
        return Role == desiredRole;
    }

    public class Address {
        private String country;
        private String city;

        public Address(String country, String city) {
            this.country = country;
            this.city = city;
        }
    }
}