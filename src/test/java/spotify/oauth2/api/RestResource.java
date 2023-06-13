package spotify.oauth2.api;

import io.restassured.response.Response;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static spotify.oauth2.api.Route.*;
import static spotify.oauth2.api.SpecBuilder.*;

public class RestResource {
    public static Response post(String path, String accessToken, Object requestPlaylist) {
        return given(getRequestSpec()).
                    body(requestPlaylist).
                    header("Authorization", "Bearer " + accessToken).
                when().
                    post(path).
                then().
                    spec(getResponseSpec()).
                    extract().response();
    }

    public static Response postAccount(HashMap<String , String> formParams) {
        return given(getAccountRequestSpec()).
                    formParams(formParams).
                when().
                    post(API + TOKEN).
                then().spec(getResponseSpec()).
                    extract().response();
    }

    public static Response get(String path, String accessToken) {
        return given(getRequestSpec()).
                    header("Authorization", "Bearer " + accessToken).
                when().
                    get(path).
                then().
                    spec(getResponseSpec()).
                    extract().response();
    }

    public static Response update(String path, String accessToken, Object requestPlaylist) {
        return given(getRequestSpec()).
                    body(requestPlaylist).
                    header("Authorization", "Bearer " + accessToken).
                when().
                    put(path).
                then().
                    spec(getResponseSpec()).
                    extract().response();
    }
}
