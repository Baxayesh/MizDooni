package ir.ut.ie.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import ir.ut.ie.exceptions.ExternalServiceException;
import ir.ut.ie.exceptions.MizdooniNotAuthenticatedException;
import ir.ut.ie.utils.OAuthAccessToken;
import ir.ut.ie.utils.OAuthUser;
import lombok.RequiredArgsConstructor;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${spring.security.oauth2.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.google.client-secret}")
    private String clientSecret;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String SCOPES =
            "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";

    private static final String REDIRECT_URI = "http://localhost:3001/google";

    private OAuthAccessToken getAccessToken(String userCode)
            throws MizdooniNotAuthenticatedException, ExternalServiceException {

        var url = new HttpUrl
                .Builder()
                .scheme("https")
                .host("oauth2.googleapis.com")
                .addPathSegment("token")
                .addQueryParameter("code", userCode)
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("client_secret", clientSecret)
                .addQueryParameter("redirect_uri", REDIRECT_URI)
                .addQueryParameter("grant_type", "authorization_code")
                .addQueryParameter("scope", SCOPES)
                .build()
                .toString();

        var body = new FormBody.Builder().build();

        var request = new Request
                .Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("user-agent", "Mizdooni")
                .post(body)
                .build();

        try (var response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                assert response.body() != null;
                return mapper.readValue(response.body().byteStream(), OAuthAccessToken.class);
            }else{
                throw new MizdooniNotAuthenticatedException();
            }
        } catch (IOException e) {
            throw new ExternalServiceException("Failed to connect to google service");
        }


    }


    private OAuthUser fetchUserDetails(OAuthAccessToken accessToken) throws ExternalServiceException {

        var url = new HttpUrl
                .Builder()
                .scheme("https")
                .host("www.googleapis.com")
                .addPathSegment("oauth2")
                .addPathSegment("v2")
                .addPathSegment("userinfo")
                .build()
                .toString();

        var request = new Request
                .Builder()
                .url(url)
                .addHeader("Authorization", accessToken.token_type() + ' ' + accessToken.access_token())
                .get()
                .build();

        try (var response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                assert response.body() != null;
                return mapper.readValue(response.body().byteStream(), OAuthUser.class);
            }else{
                throw new ExternalServiceException("Invalid response from google");
            }
        } catch (IOException e) {
            throw new ExternalServiceException("Failed to connect to google service");
        }

    }

    public OAuthUser fetchUserDetails(String userCode)
            throws MizdooniNotAuthenticatedException, ExternalServiceException {
        var accessToken = getAccessToken(userCode);
        return fetchUserDetails(accessToken);
    }
}
