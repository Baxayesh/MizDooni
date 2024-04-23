package ir.ut.ie.contracts;

import ir.ut.ie.utils.Rating;

public record RatingModel(
        double foodScore,
        double serviceScore,
        double ambianceScore,
        double overallScore,
        int reviewCount
) {

    public static RatingModel fromDomainObject(Rating model) {
        return new RatingModel(
                model.getAverageFoodScore(),
                model.getAverageServiceScore(),
                model.getAverageAmbianceScore(),
                model.getAverageOverallScore(),
                model.getReviewCount()
        );
    }
}
