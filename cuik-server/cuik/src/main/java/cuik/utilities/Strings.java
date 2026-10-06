package cuik.utilities;

public abstract class Strings {

    public static String emptyOr(String a, String b) {
        if (a == null || a.length() == 0) {
            return b;
        }
        return a;
    }
}
