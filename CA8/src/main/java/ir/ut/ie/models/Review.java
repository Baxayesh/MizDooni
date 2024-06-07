package ir.ut.ie.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "REVIEWS")
public class Review implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Client Issuer;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Restaurant Restaurant;

    @jakarta.persistence.Id
    @GeneratedValue
    private int Id;

    @Column(nullable = false)
    private double FoodScore;
    @Column(nullable = false)
    private double ServiceScore;
    @Column(nullable = false)
    private double AmbianceScore;
    @Column(nullable = false)
    private double OverallScore;

    @Column(columnDefinition="text")
    private String Comment;

    @Column(nullable = false)
    private LocalDateTime IssueTime;

    public Review(
            Restaurant restaurant,
            Client user,
            double food,
            double service,
            double ambiance,
            double overall,
            String comment
    ) {
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
