package ir.ut.ie.models;

import lombok.Getter;

public class Rating {

    private final Restaurant Restaurant;

    private double TotalFoodScore;
    private double TotalServiceScore;
    private double TotalAmbianceScore;
    private double TotalOverallScore;

    @Getter
    private int ReviewCount;

    public Rating(Restaurant restaurant){
        Restaurant = restaurant;
        TotalFoodScore = 0;
        TotalServiceScore = 0;
        TotalAmbianceScore = 0;
        TotalOverallScore = 0;
        ReviewCount = 0;
    }


    private Rating(
            Restaurant restaurant,
            double totalFoodScore,
            double totalServiceScore,
            double totalAmbianceScore,
            double totalOverallScore,
            int reviewCount
    ) {
        Restaurant = restaurant;
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

    public void ConsiderReview(Review newReview) {
        ReviewCount++;
        TotalFoodScore += newReview.getFoodScore();
        TotalServiceScore += newReview.getServiceScore();
        TotalAmbianceScore += newReview.getAmbianceScore();
        TotalOverallScore += newReview.getOverallScore();
    }

    public void UpdateReview(Review oldReview, Review newReview) {
        TotalFoodScore += newReview.getFoodScore() - oldReview.getFoodScore();
        TotalServiceScore += newReview.getServiceScore() - oldReview.getServiceScore();
        TotalAmbianceScore += newReview.getAmbianceScore() - oldReview.getAmbianceScore();
        TotalOverallScore += newReview.getOverallScore() - oldReview.getOverallScore();
    }
}
