package ir.ut.ie.controllers;

import ir.ut.ie.contracts.GoogleOauth2CallbackParams;
import ir.ut.ie.contracts.LoginRequest;
import ir.ut.ie.contracts.SignupRequest;
import ir.ut.ie.contracts.TokenModel;
import ir.ut.ie.exceptions.EmailAlreadyExits;
import ir.ut.ie.exceptions.ExternalServiceException;
import ir.ut.ie.exceptions.MizdooniNotAuthenticatedException;
import ir.ut.ie.exceptions.UserAlreadyExits;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController extends MizdooniController {

    @PostMapping("/sessions")
    public TokenModel Login(@Valid @RequestBody LoginRequest request)
            throws MizdooniNotAuthenticatedException {

        var token = authenticationService.login(request.username(), request.password());

        return TokenModel.fromDomainObject(token);
    }

    @PostMapping("/oauth2")
    public TokenModel ManageGoogleOauth2Callback(@RequestBody GoogleOauth2CallbackParams request)
            throws MizdooniNotAuthenticatedException, ExternalServiceException {

        var token = authenticationService.LoginByOAuth(request.userCode());

        return TokenModel.fromDomainObject(token);

    }


    @SneakyThrows(MizdooniNotAuthenticatedException.class)
    @PostMapping("/users")
    public TokenModel Signup(@Valid @RequestBody SignupRequest request)
            throws UserAlreadyExits, EmailAlreadyExits {

        authenticationService.addUser(
                request.role(),
                request.username(),
                request.password(),
                request.email(),
                request.country(),
                request.city()
        );

        return Login(new LoginRequest(request.username(), request.password()));
    }
}