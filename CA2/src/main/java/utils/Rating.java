package utils;

import lombok.Getter;
import models.Review;

public class Rating {
    private double TotalFoodScore;
    private double TotalServiceScore;
    private double TotalAmbianceScore;
    private double TotalOverallScore;

    @Getter
    private int ReviewCount;

    public Rating(){
        TotalFoodScore = 0;
        TotalServiceScore = 0;
        TotalAmbianceScore = 0;
        TotalOverallScore = 0;
        ReviewCount = 0;
    }


    private Rating(
            double totalFoodScore,
            double totalServiceScore,
            double totalAmbianceScore,
            double totalOverallScore,
            int reviewCount
    ) {
        TotalFoodScore = totalFoodScore;
        TotalServiceScore = totalServiceScore;
        TotalAmbianceScore = totalAmbianceScore;
        TotalOverallScore = totalOverallScore;
        ReviewCount = reviewCount;
    }

    public double getAverageFoodScore() {
        return ReviewCount == 0 ? 0 : TotalFoodScore / ReviewCount;
    }

    public double getAverageServiceScore() {
        return  ReviewCount == 0 ? 0 : TotalServiceScore / ReviewCount;
    }

    public double getAverageAmbianceScore() {
        return  ReviewCount == 0 ? 0 : TotalAmbianceScore / ReviewCount;
    }

    public double getAverageOverallScore() {
        return  ReviewCount == 0 ? 0 : TotalOverallScore / ReviewCount;
    }

    public Rating ConsiderReview(Review newReview) {
        ReviewCount++;
        TotalFoodScore += newReview.getFoodScore();
        TotalServiceScore += newReview.getServiceScore();
        TotalAmbianceScore += newReview.getAmbianceScore();
        TotalOverallScore += newReview.getOverallScore();
        return this;
    }

    public static Rating Combine(Rating right, Rating left){
        return new Rating(
                right.TotalFoodScore + left.TotalFoodScore,
                right.TotalServiceScore + left.TotalServiceScore,
                right.TotalAmbianceScore + left.TotalAmbianceScore,
                right.TotalOverallScore + left.TotalOverallScore,
                right.ReviewCount + left.ReviewCount
        );
    }
}
