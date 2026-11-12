package cuik.utilities;

import java.util.UUID;

public abstract class Strings {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String emptyOr(String a, String b) {
        if (a == null || a.length() == 0) {
            return b;
        }
        return a;
    }

    public static UUID safeParseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception ex) {
            return null;
        }
    }
}
