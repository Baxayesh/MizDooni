package ir.ut.ie.contracts;

public record RatingModel(
        double foodScore,
        double serviceScore,
        double ambianceScore,
        double overallScore,
        int reviewCount
) {

}
