package spotify.oauth2.api;

import io.restassured.response.Response;
import spotify.oauth2.utils.ConfigLoader;

import java.time.Instant;
import java.util.HashMap;

public class TokenManager {
    private static String accessToken;
    private static Instant expiryTime;
    static String clientId = ConfigLoader.getInstance().getClientId();
    static String clientSecret = ConfigLoader.getInstance().getSecret();
    static String refreshToken = ConfigLoader.getInstance().getRefreshToken();
    static String grantType = ConfigLoader.getInstance().getGrantType();

    public static String getAccessToken() {
        try {
            if (accessToken == null || Instant.now().isAfter(expiryTime)) {
                System.out.println("Renewing access token...");
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiryTime = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            }
            else {
                System.out.println("Token is valid!!!");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("ABORT!!! Failed to get access token");
        }
        return accessToken;
    }

    public static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("client_id", clientId);
        formParams.put("client_secret", clientSecret);
        formParams.put("refresh_token", refreshToken);
        formParams.put("grant_type", grantType);

        Response response = RestResource.postAccount(formParams);

        if (response.statusCode()!= 200) {
            throw new RuntimeException("Abort!!! Renew Token Failed");
        }

        return response;
    }
}
