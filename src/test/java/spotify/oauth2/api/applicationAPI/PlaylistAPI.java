package spotify.oauth2.api.applicationAPI;

import io.restassured.response.Response;
import spotify.oauth2.api.RestResource;
import spotify.oauth2.pojo.Playlist;
import spotify.oauth2.utils.ConfigLoader;

import static spotify.oauth2.api.Route.*;
import static spotify.oauth2.api.TokenManager.getAccessToken;

public class PlaylistAPI {
    static String userId = ConfigLoader.getInstance().getUserId();

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(USERS + userId + PLAYLISTS, getAccessToken(), requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist) {
        return RestResource.post(USERS + userId + PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + playlistId, getAccessToken());
    }

    public static Response update(String playlistId, Playlist requestPlaylist) {
        return RestResource.update(PLAYLISTS + playlistId, getAccessToken(), requestPlaylist);
    }
}
