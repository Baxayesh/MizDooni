package ir.ut.ie.exceptions;

public class KeyAlreadyExists extends RepositoryException {

    private static String CreateError(String key) {
        return String.format("Repository Already Contain A Key %s", key);
    }


    public KeyAlreadyExists(String key) {
        super(CreateError(key));
    }
}
