package ir.ut.ie.models;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Rating {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Restaurant Restaurant;

    @Column
    private double TotalFoodScore;
    @Column
    private double TotalServiceScore;
    @Column
    private double TotalAmbianceScore;
    @Column
    private double TotalOverallScore;

    @Getter
    @Column
    private int ReviewCount;

    public Rating(Restaurant restaurant){
        Restaurant = restaurant;
        TotalFoodScore = 0;
        TotalServiceScore = 0;
        TotalAmbianceScore = 0;
        TotalOverallScore = 0;
        ReviewCount = 0;
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
