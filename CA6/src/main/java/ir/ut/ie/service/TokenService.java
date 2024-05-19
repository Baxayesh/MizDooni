package ir.ut.ie.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import ir.ut.ie.database.UserRepository;
import ir.ut.ie.utils.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${application.security.jwt.sign-key}")
    private String SignKey;

    @Value("${application.security.jwt.expiration-duration}")
    private long TokenExpirationDuration;

    private final UserRepository UserRepo;

    public Optional<Token> parseValidToken(String jwtText){

        if (jwtText == null ||!jwtText.startsWith("Bearer ")) {
            return Optional.empty();
        }

        jwtText = jwtText.substring(7);

        try{
            var username = extractClaim(jwtText, Claims::getSubject);
            var owner = UserRepo.tryGet(username);

            if(owner.isEmpty())
                return Optional.empty();

            var token = new Token(
                    owner.get(),
                    extractClaim(jwtText, Claims::getIssuedAt),
                    extractClaim(jwtText, Claims::getExpiration),
                    jwtText
            );

            if(token.isValid()){
                return Optional.of(token);
            }


        }catch (JwtException ex){
            return Optional.empty();
        }

        return Optional.empty();
    }

    public Token createToken(UserDetails user){
        var issueTime = new Date();
        var expirationTime = new Date(issueTime.getTime() + TokenExpirationDuration);

        var tokenText = Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(issueTime)
                .setExpiration(expirationTime)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        return new Token(user, issueTime, expirationTime, tokenText);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws JwtException  {

        var claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Hex.decode(SignKey));
    }
}