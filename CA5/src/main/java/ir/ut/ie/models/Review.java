package ir.ut.ie.models;

import ir.ut.ie.exceptions.ScoreOutOfRange;
import lombok.Getter;
import ir.ut.ie.utils.PairType;

import java.time.LocalDateTime;

@Getter
public class Review extends EntityModel<PairType<String, String>> {

    private final Client Issuer;
    private final Restaurant Restaurant;
    private int Id; //TODO: give value to this field
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
            Restaurant restaurant,
            Client user,
            double food,
            double service,
            double ambiance,
            double overall,
            String comment
    ) throws ScoreOutOfRange {
        super(new PairType<>(restaurant.getName(), user.getUsername()));
        EnsureScoreIsInValidRange(food);
        EnsureScoreIsInValidRange(service);
        EnsureScoreIsInValidRange(ambiance);
        EnsureScoreIsInValidRange(overall);
        Issuer = user;
        Restaurant = restaurant;
        IssueTime = LocalDateTime.now();
        FoodScore = food;
        ServiceScore = service;
        AmbianceScore = ambiance;
        OverallScore = overall;
        Comment = comment;
    }

}
