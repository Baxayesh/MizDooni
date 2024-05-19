package ir.ut.ie.controllers;

import ir.ut.ie.contracts.LoginResponse;
import ir.ut.ie.contracts.SignupRequest;
import ir.ut.ie.exceptions.*;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController extends MizdooniController {

    @SneakyThrows(MizdooniNotAuthenticatedException.class)
    @PostMapping
    public LoginResponse Signup(@Valid @RequestBody SignupRequest request)
            throws UserAlreadyExits, EmailAlreadyExits {

        authenticationService.addUser(
                request.role(),
                request.username(),
                request.password(),
                request.email(),
                request.country(),
                request.city()
        );

        return new LoginResponse(authenticationService.login(request.username(), request.password()).getJwtText());
    }

}

