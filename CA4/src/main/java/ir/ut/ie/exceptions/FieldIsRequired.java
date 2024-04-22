package ir.ut.ie.exceptions;

public class FieldIsRequired extends MizdooniUserException {

    public FieldIsRequired(String fieldName) {
        super("Input field \"%s\" is required".formatted(fieldName));
    }
}
