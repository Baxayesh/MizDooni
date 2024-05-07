package ir.ut.ie.contracts;

import ir.ut.ie.models.Review;

import java.time.LocalDateTime;

public record ReviewModel(
        String restaurant,
        String issuer,
        double foodScore,
        double serviceScore,
        double ambianceScore,
        double overallScore,
        String comment,
        LocalDateTime issueTime
) {

    public static ReviewModel fromDomainObject(Review model) {
        return new ReviewModel(
                model.getRestaurant().getName(),
                model.getIssuer().getUsername(),
                model.getFoodScore(),
                model.getServiceScore(),
                model.getAmbianceScore(),
                model.getOverallScore(),
                model.getComment(),
                model.getIssueTime()
        );
    }

}
