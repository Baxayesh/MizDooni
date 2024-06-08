package ir.ut.ie.exceptions;

public class NotAValidDatetime extends MizdooniUserException {

    public NotAValidDatetime(String fieldName) {
        super("Input field \"%s\" should be a valid date/time".formatted(fieldName));
    }
}
