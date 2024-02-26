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

    //fields: username password, email, address, role
    private UserRole Role;
    private String role;
    private String username;
    private String password;
    private String email;
    private Address userAddress;

    static String NormalizeUsername(String username){
        return  username.strip().toUpperCase();
    }
    private List<User> users;

    public User(String username, String role, String password, String email, Address address) {
        super(NormalizeUsername(username));
        //Role = role;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        userAddress = address;
        this.users = new ArrayList<>();
    }

    public String addUser(String role, String username, String password, String email, String country, String city) {
        // Validate role
        if (!role.equals("client") && !role.equals("manager")) {
            return "Error: Invalid role. Role must be either 'client' or 'manager'.";
        }

        // Validate username
        if (username.contains(" ") || username.contains(";") || !Pattern.matches("^[a-zA-Z0-9]*$", username)) {
            return "Error: Invalid username. Username cannot contain spaces, semi-spaces, or special characters.";
        }

        // Validate email
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            return "Error: Invalid email format.";
        }

        // Check for duplicate username or email
        for (User user : users) {
            if (user.getUsername().equals(username) || user.getEmail().equals(email)) {
                return "Error: Username or email already exists.";
            }
        }

        // Validate address
        if (country == null || country.isEmpty() || city == null || city.isEmpty()) {
            return "Error: Invalid address. Address must include country and city.";
        }

        // Add user to the list
        users.add(new User(username, role, password, email, new Address(country,city)));

        return "{\"success\": true, \"data\": \"User added successfully.\"}";
    }


    public String getUsername(){
        return super.getKey();
    }


    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean RoleIs(UserRole desiredRole) {
        return Role == desiredRole;
    }

    @Override
    public boolean Is(String otherKey) {
        return super.Is(NormalizeUsername(otherKey));
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



