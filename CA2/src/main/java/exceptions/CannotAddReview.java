package exceptions;

import static defines.Errors.CANNOT_ADD_REVIEW;

public class CannotAddReview extends MizdooniUserException {


    public CannotAddReview() {
        super(CANNOT_ADD_REVIEW);
    }
}
