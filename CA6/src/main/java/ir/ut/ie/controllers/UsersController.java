package ir.ut.ie.controllers;

import ir.ut.ie.contracts.LoginResponse;
import ir.ut.ie.exceptions.*;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController extends MizdooniController {


    @SneakyThrows(MizdooniNotAuthenticatedException.class)
    @PostMapping
    public LoginResponse Signup(@RequestBody Map<String, String> request) throws InvalidAddress, InvalidUser, UserAlreadyExits, FieldIsRequired {
        var username = getRequiredField(request, "username");
        var password = getRequiredField(request, "password");

        authenticationService.addUser(
                getRequiredField(request, "role"),
                username,
                password,
                getRequiredField(request, "email"),
                getRequiredField(request, "country"),
                getRequiredField(request, "city")
        );

        return new LoginResponse(authenticationService.login(username, password).getJwtText());
    }

}

