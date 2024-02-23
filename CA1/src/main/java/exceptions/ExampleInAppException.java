package exceptions;

import static defines.Errors.EXAMPLE_IN_APP_EXCEPTION;

public class ExampleInAppException extends Exception {

    public ExampleInAppException() {
        super(EXAMPLE_IN_APP_EXCEPTION);
    }

}