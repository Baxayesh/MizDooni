package model;

import exceptions.ScoreOutOfRange;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Review {

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

    public Review(
            double food,
            double service,
            double ambiance,
            double overall,
            String comment
    ) throws ScoreOutOfRange {

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
