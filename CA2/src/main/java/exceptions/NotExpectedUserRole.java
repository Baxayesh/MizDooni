package exceptions;

import utils.UserRole;

public class NotExpectedUserRole extends MizdooniUserException {

    static String CreateMessage(UserRole expectedRole){
        return String.format("Expected User To Have Role %s", expectedRole.toString());
    }
    public NotExpectedUserRole(UserRole expectedRole) {
        super(CreateMessage(expectedRole));
    }
}

