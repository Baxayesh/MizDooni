package ir.ut.ie.database;

import ir.ut.ie.exceptions.NotExistentUser;
import ir.ut.ie.exceptions.UserAlreadyExits;
import ir.ut.ie.models.User;


public interface IUserRepository {

    void add(User user) throws UserAlreadyExits;
    User get(String username) throws NotExistentUser;
    boolean exists(String username);
}

