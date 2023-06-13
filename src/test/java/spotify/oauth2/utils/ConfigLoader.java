package spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getClientId() {
        String prop = properties.getProperty("clientId");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property clientId is not specified in the config.properties file");
    }

    public String getSecret() {
        String prop = properties.getProperty("clientSecret");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property clientSecret is not specified in the config.properties file");
    }

    public String getRefreshToken() {
        String prop = properties.getProperty("refreshToken");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property refreshToken is not specified in the config.properties file");
    }

    public String getGrantType() {
        String prop = properties.getProperty("grantType");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property grantType is not specified in the config.properties file");
    }

    public String getUserId() {
        String prop = properties.getProperty("userId");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property userId is not specified in the config.properties file");
    }

    public String getPlaylistId() {
        String prop = properties.getProperty("playlistId");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property playlistId is not specified in the config.properties file");
    }
}
