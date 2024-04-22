package ir.ut.ie.controllers;

import ir.ut.ie.exceptions.FieldIsRequired;
import ir.ut.ie.exceptions.InvalidAddress;
import ir.ut.ie.exceptions.InvalidUser;
import ir.ut.ie.exceptions.UserAlreadyExits;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController extends MizdooniController {


    @PostMapping
    public void Signup(@RequestBody Map<String, String> request) throws InvalidAddress, InvalidUser, UserAlreadyExits, FieldIsRequired {
        service.AddUser(
                getRequiredField(request, "role"),
                getRequiredField(request, "username"),
                getRequiredField(request, "password"),
                getRequiredField(request, "email"),
                getRequiredField(request, "country"),
                getRequiredField(request, "city")
        );
    }

}

