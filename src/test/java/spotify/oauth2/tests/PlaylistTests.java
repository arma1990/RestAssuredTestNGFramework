package spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import spotify.oauth2.pojo.Error;
import spotify.oauth2.pojo.Playlist;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String accessToken = "BQD1lWXXzviFymW_o8Y0CeeTQY5TtCg4lbYS-JGGvAihgH-EkOOnLNAswGGePyymHiHvy6fdS2X0B-MAboe3n5-_d99li1_L4IsHbl75ayTwtfWmIZGgCHecUgxG1ifRB5Y6tl6d8vXQeccIxFPm30cIt9UaaPYssBHbsQrYQkXgZI_rXb7p2ORnhwYBV1A8_avWCkFlM69OTeD0kLAzfmVlxyfYUS5lcLzly-2bipTvnPA5SBEs2ySrjhgX3CYT56fQxt_Qm80r";

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com/").
                setBasePath("/v1").
                addHeader("Authorization", "Bearer " + accessToken).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void createPlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("New Playlist")
                .setDescription("New playlist description")
                .setPublic(false);

        Playlist responsePlaylist = given(requestSpecification)
                    .body(requestPlaylist).
                when()
                    .post("users/7whmc0j33b634dbioux1dy55q/playlists").
                then()
                    .spec(responseSpecification)
                    .assertThat().statusCode(201)
                    .extract().response().as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void getPlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Updated Playlist Name")
                .setDescription("Updated playlist description")
                .setPublic(false);

        Playlist responsePlaylist = given(requestSpecification)
                .when()
                    .get("playlists/2WJXM3Wbse7SERKPYeCLZL").
                then()
                    .spec(responseSpecification)
                    .assertThat().statusCode(200)
                    .extract().response().as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void updatePlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("New Playlist")
                .setDescription("New playlist description")
                .setPublic(false);

        given(requestSpecification)
                .body(requestPlaylist).
                when()
                    .put("playlists/2WJXM3Wbse7SERKPYeCLZL").
                then().spec(responseSpecification)
                    .assertThat()
                    .statusCode(200);
    }

    @Test
    public void createPlaylistWithBlankName() {
        Playlist requestPlaylist = new Playlist()
                .setName("")
                .setDescription("New playlist description")
                .setPublic(false);

        Error error = given(requestSpecification)
                    .body(requestPlaylist).
                when()
                    .post("users/7whmc0j33b634dbioux1dy55q/playlists").
                then().spec(responseSpecification)
                    .assertThat()
                    .statusCode(400)
                    .extract().response().as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void createPlaylistWithExpiredToken() {
        Playlist requestPlaylist = new Playlist()
                .setName("New Playlist")
                .setDescription("New playlist description")
                .setPublic(false);

        Error error = given()
                    .baseUri("https://api.spotify.com/")
                    .basePath("/v1")
                    .header("Authorization", "Bearer " + "12344")
                    .contentType(ContentType.JSON)
                    .log().all()
                    .body(requestPlaylist).
                when()
                    .post("users/7whmc0j33b634dbioux1dy55q/playlists").
                then().spec(responseSpecification)
                    .assertThat()
                    .statusCode(401)
                    .extract().response().as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }
}
