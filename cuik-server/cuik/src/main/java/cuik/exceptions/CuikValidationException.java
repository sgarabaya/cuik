package cuik.exceptions;

record ErrorMessage(String error) {
}

public class CuikValidationException extends Exception {
    private final int status;

    public CuikValidationException(String message) {
        super(message);
        status = 400;
    }

    public CuikValidationException(String message, int status) {
        super(message);
        this.status = status;
    }

    public ErrorMessage getErrorMessage() {
        return new ErrorMessage(getMessage());
    }

    public int getStatus() {
        return this.status;
    }
}
