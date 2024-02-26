package service;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testUtils.MizdooniStubHelper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Review Tests")
public class ReviewTests {

    MizdooniStubHelper stub;

    @BeforeEach
    void SetupRequirements() {
        stub = new MizdooniStubHelper();
    }


    @Test
    void GIVEN_normalMizdooni_WHEN_addReviewWithNotExistentUsername_THEN_shouldThrowNotExistentUser()
            throws NotExistentRestaurant, NotExpectedUserRole, NotExistentUser, ScoreOutOfRange {

        var restaurant = "restaurant";
        var username = "not_existent_username";
        stub.AddAnonymousRestaurantToMizdooni(restaurant);

        assertThrows(
                NotExistentUser.class,
                () ->  stub.Mizdooni().AddReview(
                        username,
                        restaurant,
                        5,5,5,5,""
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_addReviewWithManagerUsername_THEN_shouldThrowNotExpectedUserRole()
            throws NotExistentRestaurant, NotExpectedUserRole, NotExistentUser, ScoreOutOfRange {

        var restaurant = "restaurant";
        var username = "manager";
        stub.AddAnonymousRestaurantToMizdooni(restaurant);
        stub.AddAnonymousManagerToMizdooni(username);

        assertThrows(
                NotExpectedUserRole.class,
                () ->  stub.Mizdooni().AddReview(
                        username,
                        restaurant,
                        5,5,5,5,""
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_addReviewForNotExistentRestaurant_THEN_shouldThrowNotExistentRestaurant()
            throws NotExistentRestaurant, NotExpectedUserRole, NotExistentUser, ScoreOutOfRange {

        var restaurant = "not_existent_restaurant";
        var username = "user";
        stub.AddAnonymousCustomerToMizdooni(username);

        assertThrows(
                NotExistentRestaurant.class,
                () ->  stub.Mizdooni().AddReview(
                        username,
                        restaurant,
                        5,5,5,5,""
                )
        );

    }


    @Test
    void GIVEN_normalMizdooni_WHEN_addReviewWithOutOfRangeScores_THEN_shouldThrowScoreOutOfRange()
            throws NotExistentRestaurant, NotExpectedUserRole, NotExistentUser, ScoreOutOfRange {

        var restaurant = "restaurant";
        var username = "user";
        stub.AddAnonymousRestaurantToMizdooni(restaurant);
        stub.AddAnonymousCustomerToMizdooni(username);

        assertThrows(
                ScoreOutOfRange.class,
                () ->  stub.Mizdooni().AddReview(
                        username,
                        restaurant,
                        -1,6,-1,6,""
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_addAValidReview_THEN_reviewShouldBeRegisteredInRestaurant()
            throws NotExistentRestaurant, NotExpectedUserRole, NotExistentUser, ScoreOutOfRange, KeyNotFound {

        var restaurant = "restaurant";
        var username = "user";
        var comment  = "comment";

        stub.AddAnonymousRestaurantToMizdooni(restaurant);
        stub.AddAnonymousCustomerToMizdooni(username);

        var callTime = LocalDateTime.now();

        stub.Mizdooni().AddReview(
                username,
                restaurant,
                5,5,5,5,comment
        );

        stub.AssertReviewExists(
                username,
                restaurant,
                5,5,5,5,
                comment,
                callTime
        );
    }

}
