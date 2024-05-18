package ir.ut.ie.controllers;

import ir.ut.ie.contracts.LoginResponse;
import ir.ut.ie.exceptions.FieldIsRequired;
import ir.ut.ie.exceptions.MizdooniNotAuthenticatedException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sessions")
public class SessionController extends MizdooniController {

    @PostMapping
    public LoginResponse Login(@RequestBody Map<String, String> requestBody)
            throws MizdooniNotAuthenticatedException, FieldIsRequired {

        var username = getRequiredField(requestBody, "username");
        var password = getRequiredField(requestBody, "password");

        var token = authenticationService.login(username, password);

        return new LoginResponse(token.getJwtText());
    }

}