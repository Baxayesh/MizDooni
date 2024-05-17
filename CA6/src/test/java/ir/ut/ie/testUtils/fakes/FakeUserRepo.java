package ir.ut.ie.testUtils.fakes;

import ir.ut.ie.database.IUserRepository;
import ir.ut.ie.exceptions.NotExistentUser;
import ir.ut.ie.exceptions.UserAlreadyExits;
import ir.ut.ie.models.User;

import java.util.HashMap;
import java.util.Map;

public class FakeUserRepo implements IUserRepository {

    Map<String, User> memory;

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

    public boolean exists(String username){
        return memory.containsKey(username);
    }
}

