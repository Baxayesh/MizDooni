package ir.ut.ie.controllers;

import ir.ut.ie.exceptions.FieldIsRequired;
import ir.ut.ie.exceptions.MizdooniNotAuthenticatedException;
import ir.ut.ie.exceptions.MizdooniNotAuthorizedException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sessions")
public class SessionController extends MizdooniController {

    @PostMapping
    public void Login(@RequestBody Map<String, String> requestBody)
            throws MizdooniNotAuthenticatedException, FieldIsRequired {

        var username = getRequiredField(requestBody, "username");
        var password = getRequiredField(requestBody, "password");

        service.Login(username, password);
    }

    @DeleteMapping
    public void Logout() throws MizdooniNotAuthorizedException {
        service.Logout();
    }

}