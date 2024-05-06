package ir.ut.ie.exceptions;

import ir.ut.ie.utils.UserRole;

public class NotExpectedUserRole extends MizdooniNotAuthorizedException {

    static String CreateMessage(UserRole expectedRole){
        return String.format("Expected User To Have Role %s", expectedRole.toString());
    }

    public NotExpectedUserRole(UserRole expectedRole) {
        super(CreateMessage(expectedRole));
    }
}

