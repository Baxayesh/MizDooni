package ir.ut.ie.service;

import ir.ut.ie.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ir.ut.ie.testUtils.MizdooniStubHelper;

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
    void GIVEN_normalMizdooni_WHEN_addReviewWithNotExistentUsername_THEN_shouldThrowNotExistentUser() {

        var restaurant = "restaurant";
        var username = "not_existent_username";
        stub.AddAnonymousRestaurant(restaurant);

        assertThrows(
                NotExistentUser.class,
                () ->  stub.Mizdooni().addReview(
                        username,
                        restaurant,
                        5,5,5,5,""
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_addReviewWithManagerUsername_THEN_shouldThrowNotExistentUser() {

        var restaurant = "restaurant";
        var username = "manager";
        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousManager(username);

        assertThrows(
                NotExistentUser.class,
                () ->  stub.Mizdooni().addReview(
                        username,
                        restaurant,
                        5,5,5,5,""
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_addReviewForNotExistentRestaurant_THEN_shouldThrowNotExistentRestaurant() {

        var restaurant = "not_existent_restaurant";
        var username = "user";
        stub.AddAnonymousClient(username);

        assertThrows(
                NotExistentRestaurant.class,
                () ->  stub.Mizdooni().addReview(
                        username,
                        restaurant,
                        5,5,5,5,""
                )
        );

    }


    @Test
    void GIVEN_userDoesNotHaveAnyPreviousPassedReserve_WHEN_addAValidReview_THEN_CannotAddReviewShouldBeThrown() {

        var restaurant = "restaurant";
        var username = "user";
        var comment  = "comment";

        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousClient(username);

        assertThrows(
                NotAllowedToAddReview.class,
                () ->
                    stub.Mizdooni().addReview(
                            username,
                            restaurant,
                            5,5,5,5,comment
                    )
        );
    }

    @Test
    void GIVEN_userHaveAPreviousPassedReserve_WHEN_addAValidReview_THEN_ReviewShouldBeAdded()
            throws NotExistentRestaurant, NotExistentUser, NotAllowedToAddReview {

        var restaurant = "restaurant";
        var username = "user";
        var comment  = "comment";

        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousClient(username);
        var table = stub.AddAnonymousTable(restaurant);
        stub.AddPassedReserve(username, restaurant, table);

        var callTime = LocalDateTime.now();

        stub.Mizdooni().addReview(
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
