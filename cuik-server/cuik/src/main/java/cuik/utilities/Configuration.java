package cuik.utilities;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

class ConfigMap extends HashMap<String, String> {}

public abstract class Configuration {

    private static Map<String, String> configValues;

    public static void loadConfig() throws Exception {
        if (configValues == null) {
            var configFile = new File("./config.json");

            if (configFile.exists()) {
                var input = new FileInputStream(configFile);
                var json = Transform.fromBytes(input.readAllBytes());
                input.close();

                configValues = new Gson().fromJson(json, ConfigMap.class);
            }
        }
    }

    public static String get(String key) {
        return get(key, "");
    }

    public static String get(String key, String _default) {
        if (configValues.containsKey(key)) return configValues.get(key);

        var prop = System.getProperty(key);

        if (prop == null || prop.isEmpty()) prop = System.getenv(key);

        configValues.put(
            key,
            prop != null && !prop.isEmpty() ? prop : _default
        );
        return configValues.get(key);
    }

    public static String getConnectionString() {
        return get("DB_CONNECTION_STRING");
    }

    public static String getDatabaseUser() {
        return get("DB_USER");
    }

    public static String getDatabasePassword() {
        return get("DB_PWD");
    }

    public static String getJwtSecret() {
        return get("JWT_SECRET");
    }

    public static String getStaticDir() {
        return get("WWW_DIR");
    }
}
