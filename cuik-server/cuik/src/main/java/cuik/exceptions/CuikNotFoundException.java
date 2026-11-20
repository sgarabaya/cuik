package cuik.exceptions;

import cuik.utilities.Messages;

public class CuikNotFoundException extends CuikValidationException {
    public CuikNotFoundException() {
        super(Messages.NotFound, 404);
    }
}
