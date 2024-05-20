package ir.ut.ie.testUtils.fakes;

import ir.ut.ie.database.IUserRepository;
import ir.ut.ie.exceptions.EmailAlreadyExits;
import ir.ut.ie.exceptions.NotExistentUser;
import ir.ut.ie.exceptions.UserAlreadyExits;
import ir.ut.ie.models.User;
import ir.ut.ie.utils.OAuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeUserRepo implements IUserRepository {

    final Map<String, User> memory;

    public FakeUserRepo(){
        memory = new HashMap<>();
    }

    public void add(User user) throws UserAlreadyExits{
        if(exists(user.getUsername()))
            throw new UserAlreadyExits();

        memory.put(user.getUsername(), user);
    }

    public User get(String username) throws NotExistentUser {

        if (!exists(username))
            throw new NotExistentUser();

        return memory.get(username);
    }

    @Override
    public boolean emailExists(String email) {
        return false;
    }

    public boolean exists(String username){
        return memory.containsKey(username);
    }

    @Override
    public Optional<User> tryGet(String username) {

        if (!exists(username))
            return Optional.empty();

        return Optional.of(memory.get(username));
    }

    @Override
    public User update(OAuthUser userData) throws EmailAlreadyExits, UserAlreadyExits {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

