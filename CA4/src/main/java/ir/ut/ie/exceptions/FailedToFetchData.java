package ir.ut.ie.exceptions;

public class FailedToFetchData extends MizdooniInternalException {

    public FailedToFetchData(String details) {
        super("Failed to fetch data from external service : %s".formatted(details));
    }

    public FailedToFetchData(Exception cause) {
        super("Failed to fetch data from external service. See inner exception for more details", cause);
    }


}
