package models;

import exceptions.ScoreOutOfRange;
import lombok.Getter;
import utils.PairType;

import java.time.LocalDateTime;

@Getter
public class Review extends EntityModel<PairType<String, String>> {

    private final double FoodScore;
    private final double ServiceScore;
    private final double AmbianceScore;
    private final double OverallScore;
    private final String Comment;

    private final LocalDateTime IssueTime;

    static void EnsureScoreIsInValidRange(double score) throws ScoreOutOfRange {
        if(score < 0 || score > 5){
            throw new ScoreOutOfRange();
        }
    }

    public String getIssuerUsername(){
        return super.getKey().getSecond();
    }

    public String getRestaurantName(){
        return super.getKey().getFirst();
    }

    public Review(
            String restaurant,
            String user,
            double food,
            double service,
            double ambiance,
            double overall,
            String comment
    ) throws ScoreOutOfRange {
        super(new PairType<>(restaurant, user));
        EnsureScoreIsInValidRange(food);
        EnsureScoreIsInValidRange(service);
        EnsureScoreIsInValidRange(ambiance);
        EnsureScoreIsInValidRange(overall);
        IssueTime = LocalDateTime.now();
        FoodScore = food;
        ServiceScore = service;
        AmbianceScore = ambiance;
        OverallScore = overall;
        Comment = comment;
    }


}
