package ir.ut.ie.utils;

public record OAuthAccessToken(
        String access_token,
        String id_token,
        Long expires_in,
        String token_type,
        String scope,
        String refresh_token
) {

}
