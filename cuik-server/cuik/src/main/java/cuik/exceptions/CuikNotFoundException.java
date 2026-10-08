package cuik.exceptions;

public class CuikNotFoundException extends CuikValidationException {
    public CuikNotFoundException() {
        super("Not found", 404);
    }
}
