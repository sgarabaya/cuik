package cuik.utilities;

public class CuikInternalException extends Exception {
    public CuikInternalException(String message) {
        super(message);
    }

    public CuikInternalException(String message, Exception ex) {
        super(message, ex);
    }
}
