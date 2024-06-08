package ir.ut.ie.contracts;

import org.hibernate.validator.constraints.Range;

public record PostReviewRequest(

        @Range(min = 1, max = 5)
        double foodRate,
        @Range(min = 1, max = 5)
        double ambientRate,
        @Range(min = 1, max = 5)
        double serviceRate,
        @Range(min = 1, max = 5)
        double overallRate,

        String comment

) {

}
