package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import utils.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import exceptions.*;
import ui.ConsoleMizdooni;
@Getter
@Setter
public class User extends EntityModel<String> {

    private String role;
    private String username;
    private String password;
    private String email;
    private Address userAddress;
    private List<User> users;

    public User(String username, String role, String password, String email, Address address) {
        super(username);
        //Role = role;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        userAddress = address;
        this.users = new ArrayList<>();
    }

    public void addUser(String username, String role, String password, String email, Address address) throws JsonProcessingException {
        if (!role.equals("client") && !role.equals("manager")){
            Exception e = new NoUserAdded();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));

        }
        if (username.contains(" ") || username.contains(";") || !Pattern.matches("^[a-zA-Z0-9]*$", username)){
            Exception e = new NoUserAdded();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)){
            Exception e = new NoUserAdded();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
        }
        for (User user : users) {
            if (user.getUsername().equals(username) || user.getEmail().equals(email)) {
                // "Error: Username or email already exists."
                Exception e = new UserAlreadyExits();
                ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));

            }
        }
        if (address.country == null || address.country.isEmpty() || address.city == null || address.city.isEmpty()) {
            // "Error: Invalid address. Address must include country and city.";
            Exception e = new InvalidAddress();
            ConsoleMizdooni.printOutput(new Output(false, e.getMessage()));
        }
        ConsoleMizdooni.printOutput(new Output(true, "User added successfully."));
    }

    public boolean RoleIs(UserRole desiredRole) {
        return role.equalsIgnoreCase(desiredRole.toString());
    }

    public static class Address {
        private String country;
        private String city;

        public Address(String country, String city) {
            this.country = country;
            this.city = city;
        }


    }
}