package ir.ut.ie.controllers;

import ir.ut.ie.contracts.PagedResponse;
import ir.ut.ie.contracts.PostReviewRequest;
import ir.ut.ie.contracts.ReviewModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.utils.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
@RestController
@RequestMapping("/reviews")
public class ReviewsController extends MizdooniController {


    @PostMapping
    @SneakyThrows(NotExistentUser.class)
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public void PostReview(
            @RequestParam(name="restaurant") String restaurantName,
            @Valid @RequestBody PostReviewRequest request
    ) throws NotExistentRestaurant, NotAllowedToAddReview {

        var issuer = getCurrentUser();

        mizdooni.addReview(
                issuer.getUsername(),
                restaurantName,
                request.foodRate(),
                request.serviceRate(),
                request.ambientRate(),
                request.overallRate(),
                request.comment()
        );
    }

    @GetMapping(params = {"restaurant"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public PagedResponse<ReviewModel> GetAllReviews(
            @RequestParam(name="restaurant") String restaurantName,
            @Positive @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @Positive @Max(100) @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ){

        var allCount = mizdooni.getReviewCount(restaurantName);
        var reviews = mizdooni.getReviews(restaurantName, offset, limit);
        var reviewModels =
                Arrays.stream(reviews)
                .map(ReviewModel::fromDomainObject)
                .toArray(ReviewModel[]::new);

        return new PagedResponse<>(allCount, offset, limit, reviewModels);
    }

}