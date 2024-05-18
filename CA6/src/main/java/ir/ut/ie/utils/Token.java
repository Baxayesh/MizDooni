package ir.ut.ie.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class Token {


    private final UserDetails Owner;

    private final Date IssueTime;

    private final Date Expiration;

    private final String JwtText;


    public boolean isValid(){
        return new Date().before(Expiration);
    }
}