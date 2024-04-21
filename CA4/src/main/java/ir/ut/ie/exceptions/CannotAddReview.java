package ir.ut.ie.exceptions;;

import static ir.ut.ie.defines.Errors.CANNOT_ADD_REVIEW;

public class CannotAddReview extends MizdooniUserException {


    public CannotAddReview() {
        super(CANNOT_ADD_REVIEW);
    }
}
