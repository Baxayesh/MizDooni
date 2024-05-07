package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import ir.ut.ie.exceptions.InvalidUser;

public class Manager extends User {

    public Manager(String username, String password, String email, String country, String city)
            throws InvalidAddress, InvalidUser {
        super(username, password, email, country, city);
    }
}
