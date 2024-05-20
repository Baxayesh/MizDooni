package ir.ut.ie.service;


import ir.ut.ie.exceptions.MizdooniNotAuthenticatedException;
import ir.ut.ie.utils.OAuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.*;
import java.net.http.HttpHeaders;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${spring.security.oauth2.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.google.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    record Response(int status, String body){}

    Response SendRequest(String uri, String uriParamsString) throws IOException, URISyntaxException {
        URL url = new URI(uri).toURL();
        var con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        con.setDoOutput(true);
        var out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(uriParamsString);
        out.flush();
        out.close();

        int status = con.getResponseCode();

        Reader streamReader = null;

        if (status > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        var in = new BufferedReader(streamReader);
        String inputLine;
        var content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return new Response(status, content.toString());
    }


    private String getAccessToken(String userCode) throws MizdooniNotAuthenticatedException {
        return "";

    }

    private String fetchEmail(String accessToken) {
        return "";
    }

    private String fetchUsername(String accessToken) {
        return "";
    }

    public OAuthUser FetchUserDetails(String userCode) throws MizdooniNotAuthenticatedException {
        var accessToken = getAccessToken(userCode);
        return new OAuthUser(
                fetchUsername(accessToken),
                fetchEmail(accessToken)
        );
    }
}
