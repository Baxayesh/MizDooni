package ir.ut.ie.database;

import ir.ut.ie.exceptions.EmailAlreadyExits;
import ir.ut.ie.exceptions.NotExistentUser;
import ir.ut.ie.exceptions.UserAlreadyExits;
import ir.ut.ie.models.User;
import ir.ut.ie.utils.OAuthUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public interface IUserRepository extends UserDetailsService {

    void add(User user) throws UserAlreadyExits, EmailAlreadyExits;
    User get(String username) throws NotExistentUser;
    boolean emailExists(String email);
    Optional<User> getByEmail(String email);
    boolean exists(String username);
    Optional<User> tryGet(String username);

    User updateName(String email, String name) throws NotExistentUser;
}

