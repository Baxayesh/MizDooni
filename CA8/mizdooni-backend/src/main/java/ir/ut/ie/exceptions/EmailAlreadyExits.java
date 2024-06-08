package ir.ut.ie.exceptions;

public class EmailAlreadyExits extends MizdooniUserException {
    public EmailAlreadyExits() {
        super("User Email Already Exists");
    }
}
