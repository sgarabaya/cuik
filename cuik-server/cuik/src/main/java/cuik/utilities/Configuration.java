package cuik.utilities;

public abstract class Configuration {
    public static String get(String key) {
        return get(key, "");
    }

    public static String get(String key, String _default) {
        var prop = System.getProperty(key);

        if (prop == null || prop.isEmpty())
            prop = System.getenv(key);

        return (prop != null && !prop.isEmpty()) ? prop : _default;
    }

    public static String getConnectionString() {
        return get("DB_CONNECTION_STRING");
    }
}
