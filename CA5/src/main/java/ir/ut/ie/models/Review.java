package ir.ut.ie.models;

import ir.ut.ie.exceptions.ScoreOutOfRange;
import jakarta.persistence.*;
import lombok.Getter;
import ir.ut.ie.utils.PairType;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Review extends EntityModel<PairType<String, String>> {

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Client Issuer;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Restaurant Restaurant;

    @jakarta.persistence.Id
    private int Id; //TODO: give value to this field

    @Column(nullable = false)
    private double FoodScore;
    @Column(nullable = false)
    private double ServiceScore;
    @Column(nullable = false)
    private double AmbianceScore;
    @Column(nullable = false)
    private double OverallScore;

    @Column
    private String Comment;

    @Column(nullable = false)
    private LocalDateTime IssueTime;

    static void EnsureScoreIsInValidRange(double score) throws ScoreOutOfRange {
        if(score < 0 || score > 5){
            throw new ScoreOutOfRange();
        }
    }

    @Override
    public PairType<String, String> getKey(){
        return new PairType<>(this.Restaurant.getName(), this.getIssuer().getUsername());
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
