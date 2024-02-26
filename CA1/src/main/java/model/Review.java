package model;

import exceptions.ScoreOutOfRange;

import java.time.LocalDateTime;

public class Review {

    double Food;
    double Service;
    double Ambiance;
    double Overall;
    String Comment;

    LocalDateTime IssueTime;

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
        Food = food;
        Service = service;
        Ambiance = ambiance;
        Overall = overall;
        Comment = comment;
    }


}
