package ir.ut.ie.controllers;

import ir.ut.ie.contracts.PagedResponse;
import ir.ut.ie.contracts.ReviewModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.utils.UserRole;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewsController extends MizdooniController {

    @PostMapping
    @PutMapping
    @SneakyThrows(NotExistentUser.class)
    public void PostReview(
            @RequestParam(name="restaurant") String restaurantName,
            @RequestBody Map<String, String> request
    ) throws MizdooniNotAuthorizedException, FieldIsRequired, NotAValidNumber,
            NotExistentRestaurant, NotAllowedToAddReview, ScoreOutOfRange {

        service.EnsureLoggedIn(UserRole.Client);
        var issuer = service.getLoggedIn();

        var foodScore = getRequiredNumberField(request, "foodRate");
        var ambientScore = getRequiredNumberField(request, "ambientRate");
        var serviceScore = getRequiredNumberField(request, "serviceRate");
        var overallScore = getRequiredNumberField(request, "overallRate");
        var comment = request.get("comment");

        service.AddReview(
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
    public PagedResponse<ReviewModel> GetAllReviews(
            @RequestParam(name="restaurant") String restaurantName,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    )
            throws MizdooniNotAuthorizedException {

        limit = Math.min(50, limit);

        service.EnsureLoggedIn();

        var reviews = service.getReviews(restaurantName);
        var reviewModels = Arrays.stream(reviews)
                .skip(offset)
                .limit(limit)
                .map(ReviewModel::fromDomainObject)
                .toArray(ReviewModel[]::new);

        return new PagedResponse<>(reviews.length, offset, limit, reviewModels);
    }

    @GetMapping(params = {"restaurant", "issuer"})
    public ReviewModel GetOneReview(
            @RequestParam(name="restaurant") String restaurantName,
            @RequestParam(name="issuer") String issuerUsername)
            throws MizdooniNotAuthorizedException, MizdooniNotFoundException {

        service.EnsureLoggedIn();

        var review = service.FindReview(restaurantName, issuerUsername);

        return ReviewModel.fromDomainObject(review);
    }
}