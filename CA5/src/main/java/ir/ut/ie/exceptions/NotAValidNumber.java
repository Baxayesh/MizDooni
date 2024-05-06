package ir.ut.ie.exceptions;

public class NotAValidNumber extends MizdooniUserException {

    public NotAValidNumber(String fieldName) {
        super("Input field \"%s\" should be a valid number".formatted(fieldName));
    }
}
