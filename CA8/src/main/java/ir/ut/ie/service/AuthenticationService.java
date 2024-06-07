package ir.ut.ie.service;


import ir.ut.ie.database.IUserRepository;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Client;
import ir.ut.ie.models.Manager;
import ir.ut.ie.utils.OAuthUser;
import ir.ut.ie.utils.Token;
import ir.ut.ie.models.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final OAuthService oAuthService;

    @Transactional
    public void addUser(
            String role,
            String username,
            String password,
            String email,
            String country,
            String city
    ) throws UserAlreadyExits, EmailAlreadyExits {

        var encodedPassword = passwordEncoder.encode(password);
        User user;
        if(role.equals("manager")) {
            user = new Manager(username, encodedPassword, email, country, city);
        } else {
            user = new Client(username, encodedPassword, email, country, city);
        }

        userRepo.add(user);
    }

    @Transactional
    public Token login(String username, String password) throws MizdooniNotAuthenticatedException {

        try{
            var user = userRepo
                    .tryGet(username)
                    .orElseThrow(MizdooniNotAuthenticatedException::new);

            if(user.getEncodedPassword() == null)
                throw new MizdooniNotAuthenticatedException("Should Use Google OAuth to Login");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );

            return tokenService.createToken(user);

        } catch (org.springframework.security.core.AuthenticationException ex){
            throw new MizdooniNotAuthenticatedException();
        }

    }

    @Transactional
    public Token LoginByOAuth(String userCode) throws MizdooniNotAuthenticatedException, ExternalServiceException {
        var userData = oAuthService.fetchUserDetails(userCode);
        var user = upsertUser(userData.email(), userData.name());
        return tokenService.createToken(user);
    }


    @SneakyThrows
    public User upsertUser(String email, String name){
        if(userRepo.emailExists(email)){
            return userRepo.updateName(email, name);
        }

        var user = new Client(name, null, email, "Iran", "Tehran");
        userRepo.add(user);
        return user;
    }

}
