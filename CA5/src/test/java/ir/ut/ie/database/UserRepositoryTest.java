package ir.ut.ie.database;

import ir.ut.ie.application.MizdooniApplication;
import ir.ut.ie.exceptions.InvalidAddress;
import ir.ut.ie.exceptions.InvalidUser;
import ir.ut.ie.exceptions.NotExistentUser;
import ir.ut.ie.exceptions.UserAlreadyExits;
import ir.ut.ie.models.Client;
import ir.ut.ie.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MizdooniApplication.class)
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void GIVEN__WHEN_addingANewUser_THEN_userShouldBeAddedToDb() throws InvalidAddress, InvalidUser, UserAlreadyExits, NotExistentUser {

        var user = new Client("user", "pass",  "user@gmail.com", "iran", "tehran");
        
        userRepository.add(user);

        var userInDb = userRepository.get("user");
        assertEquals(user, userInDb);

    }


}