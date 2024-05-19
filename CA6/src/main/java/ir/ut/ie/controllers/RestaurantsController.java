package ir.ut.ie.controllers;

import ir.ut.ie.contracts.*;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.utils.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController extends MizdooniController {

    static final int RECOMMENDED_RESTAURANTS_COUNT = 6;

    @SneakyThrows(NotExistentUser.class)
    @PostMapping
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    public void AddRestaurant(@Valid @RequestBody AddRestaurantRequest request) throws RestaurantAlreadyExists {

        var owner = getCurrentUser();

        mizdooni.addRestaurant(
                request.name(),
                owner.getUsername(),
                request.type(),
                request.openTime(),
                request.closeTime(),
                request.description(),
                request.country(),
                request.city(),
                request.street(),
                request.image()
        );
    }

    @GetMapping
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    public RestaurantModel[] GetManagerRestaurants() {

        var owner = getCurrentUser();

        return Arrays.stream(mizdooni.getRestaurantsFor(owner.getUsername()))
                .map(RestaurantModel::fromDomainObject)
                .toArray(RestaurantModel[]::new);
    }

    PagedResponse<RestaurantModel> pageResults(Restaurant[] allItems, int offset, int limit){
        return new PagedResponse<>(
                allItems.length,
                offset,
                limit,
                Arrays.stream(allItems)
                        .skip(offset)
                        .limit(limit)
                        .map(RestaurantModel::fromDomainObject)
                        .toArray(RestaurantModel[]::new)
        );
    }

    @GetMapping(params = {"name"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public PagedResponse<RestaurantModel> SearchByName(
            @Size(min = 2) @RequestParam(name="name") String name,
            @Positive @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @Positive @Max(100) @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) {
        return pageResults(mizdooni.searchRestaurantByName(name, offset, limit), offset, limit);
    }

    @GetMapping(params = {"type"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public PagedResponse<RestaurantModel> SearchByType(
            @RequestParam(name="type") String type,
            @Positive @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @Positive @Max(100) @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) {
        return pageResults(mizdooni.searchRestaurantByType(type, offset, limit), offset, limit);
    }

    @GetMapping(params = {"location"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public PagedResponse<RestaurantModel> SearchByLocation(
            @RequestParam(name="location") String location,
            @Positive @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @Positive @Max(100) @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) {
        return pageResults(mizdooni.searchRestaurantByLocation(location, offset, limit), offset, limit);
    }

    Restaurant[] FetchRecommendations(String recommendingMethod) {
        return switch (recommendingMethod) {
            case "userLocation" -> {
                var user = getCurrentUser();
                try {
                    yield mizdooni.getBestRestaurantsFor(user.getUsername(), RECOMMENDED_RESTAURANTS_COUNT);
                } catch (NotExistentUser e) {
                    yield mizdooni.getBestRestaurants(RECOMMENDED_RESTAURANTS_COUNT);
                }
            }
            case "rating" -> mizdooni.getBestRestaurants(RECOMMENDED_RESTAURANTS_COUNT);
            default -> new Restaurant[0];
        };
    }


    @GetMapping(params = {"recommendBy"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public RestaurantModel[] Recommend(
        @Pattern(regexp = "(userLocation|rating)") @RequestParam(name="recommendBy") String recommendingMethod
    ) {

        var restaurants = FetchRecommendations(recommendingMethod);
        return Arrays.stream(restaurants).map(RestaurantModel::fromDomainObject).toArray(RestaurantModel[]::new);
    }

    @GetMapping(path = "/{name}")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public RestaurantModel GetRestaurant(
            @PathVariable(name = "name") String restaurantName
    ) throws NotExistentRestaurant {

        return RestaurantModel.fromDomainObject(mizdooni.findRestaurant(restaurantName));
    }

    @GetMapping(path = "/{name}/availableTimeSlots", params={"onDate", "requestedSeats"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public LocalTime[] GetAvailableReserveSlots(
            @PathVariable(name = "name") String restaurantName,
            @FutureOrPresent @RequestParam(name = "onDate") LocalDate requestDate,
            @Positive @RequestParam(name = "requestedSeats") int requestedSeats
    ) throws NotExistentRestaurant {

        return mizdooni.getAvailableTimes(restaurantName, requestDate, requestedSeats);
    }

}