package ir.ut.ie.controllers;

import ir.ut.ie.contracts.PagedResponse;
import ir.ut.ie.contracts.ReviewModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.utils.UserRole;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewsController extends MizdooniController {

    @PostMapping
    @SneakyThrows(NotExistentUser.class)
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public void PostReview(
            @RequestParam(name="restaurant") String restaurantName,
            @RequestBody Map<String, String> request
    ) throws MizdooniNotAuthorizedException, FieldIsRequired, NotAValidNumber,
            NotExistentRestaurant, NotAllowedToAddReview, ScoreOutOfRange {

        var issuer = getCurrentUser();

        var foodScore = getRequiredNumberField(request, "foodRate");
        var ambientScore = getRequiredNumberField(request, "ambientRate");
        var serviceScore = getRequiredNumberField(request, "serviceRate");
        var overallScore = getRequiredNumberField(request, "overallRate");
        var comment = request.get("comment");

        mizdooni.addReview(
                issuer.getUsername(),
                restaurantName,
                foodScore,
                serviceScore,
                ambientScore,
                overallScore,
                comment
        );
    }

    @GetMapping(params = {"restaurant"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public PagedResponse<ReviewModel> GetAllReviews(
            @RequestParam(name="restaurant") String restaurantName,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ){

        var allCount = mizdooni.getReviewCount(restaurantName);
        var reviews = mizdooni.getReviews(restaurantName, offset, limit);
        var reviewModels =
                Arrays.stream(reviews)
                .map(ReviewModel::fromDomainObject)
                .toArray(ReviewModel[]::new);

        return new PagedResponse<>(allCount, offset, limit, reviewModels);
    }

    @GetMapping(params = {"restaurant", "issuer"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public ReviewModel GetOneReview(
            @RequestParam(name="restaurant") String restaurantName,
            @RequestParam(name="issuer") String issuerUsername)
            throws MizdooniNotFoundException {

        var review = mizdooni.findReview(restaurantName, issuerUsername);

        return ReviewModel.fromDomainObject(review);
    }
}