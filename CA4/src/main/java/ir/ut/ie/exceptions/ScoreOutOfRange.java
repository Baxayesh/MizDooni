package ir.ut.ie.exceptions;;

import static ir.ut.ie.defines.Errors.SCORE_OUT_OF_RANGE;

public class ScoreOutOfRange extends MizdooniUserException {
    public ScoreOutOfRange() {

        super(SCORE_OUT_OF_RANGE);
    }
}
