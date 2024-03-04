package exceptions;

import static defines.Errors.SCORE_OUT_OF_RANGE;

public class ScoreOutOfRange extends MizdooniUserException {
    public ScoreOutOfRange() {

        super(SCORE_OUT_OF_RANGE);
    }
}
