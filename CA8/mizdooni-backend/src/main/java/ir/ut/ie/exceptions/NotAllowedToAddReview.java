package ir.ut.ie.exceptions;

import static ir.ut.ie.defines.Errors.CANNOT_ADD_REVIEW;

public class NotAllowedToAddReview extends MizdooniUserException {


    public NotAllowedToAddReview() {
        super(CANNOT_ADD_REVIEW);
    }
}
