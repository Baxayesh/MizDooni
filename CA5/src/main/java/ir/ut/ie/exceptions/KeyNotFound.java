package ir.ut.ie.exceptions;;

public class KeyNotFound extends RepositoryException {

    private static String CreateError(String key) {
        return String.format("Key %s Not Found In Repository", key);
    }

    public KeyNotFound(String key) {
        super(CreateError(key));
    }


}
