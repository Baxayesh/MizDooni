package ir.ut.ie.controllers;

import ir.ut.ie.contracts.LoginRequest;
import ir.ut.ie.contracts.LoginResponse;
import ir.ut.ie.exceptions.MizdooniNotAuthenticatedException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
public class SessionController extends MizdooniController {

    @PostMapping
    public LoginResponse Login(@Valid @RequestBody LoginRequest request)
            throws MizdooniNotAuthenticatedException {

        var token = authenticationService.login(request.username(), request.password());

        return new LoginResponse(token.getJwtText());
    }

    @PostMapping("/oauth2")
    public LoginResponse ManageGoogleOauth2Callback(@RequestBody String userCode)
            throws MizdooniNotAuthenticatedException {

        var token = authenticationService.LoginByOAuth(userCode);

        return new LoginResponse(token.getJwtText());

    }

}