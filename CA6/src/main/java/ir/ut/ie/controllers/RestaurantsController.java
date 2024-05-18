package ir.ut.ie.controllers;

import ir.ut.ie.contracts.*;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.utils.UserRole;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController extends MizdooniController {

    static final int RECOMMENDED_RESTAURANTS_COUNT = 6;

    //type format 23:59
    @SneakyThrows(NotExistentUser.class)
    @PostMapping
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    public void AddRestaurant(@RequestBody Map<String, String> request) throws FieldIsRequired,
            NotAValidDatetime, RestaurantAlreadyExists, MizdooniNotAuthorizedException, InvalidAddress {

        var owner = getCurrentUser();

        mizdooni.addRestaurant(
                getRequiredField(request, "name"),
                owner.getUsername(),
                getRequiredField(request, "type"),
                toTime(getRequiredField(request, "openTime"), "openTime"),
                toTime(getRequiredField(request, "closeTime"), "closeTime"),
                getRequiredField(request, "description"),
                getRequiredField(request, "country"),
                getRequiredField(request, "city"),
                getRequiredField(request, "street"),
                getRequiredField(request, "image")
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
            @RequestParam(name="name") String name,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) {
        return pageResults(mizdooni.searchRestaurantByName(name, offset, limit), offset, limit);
    }

    @GetMapping(params = {"type"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public PagedResponse<RestaurantModel> SearchByType(
            @RequestParam(name="type") String type,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) {
        return pageResults(mizdooni.searchRestaurantByType(type, offset, limit), offset, limit);
    }

    @GetMapping(params = {"location"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public PagedResponse<RestaurantModel> SearchByLocation(
            @RequestParam(name="location") String location,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) {
        return pageResults(mizdooni.searchRestaurantByLocation(location, offset, limit), offset, limit);
    }

    Restaurant[] FetchRecommendations(String recommendingMethod) throws MizdooniNotAuthorizedException, NotExistentUser {
        return switch (recommendingMethod) {
            case "userLocation" -> {
                var user = getCurrentUser();
                yield mizdooni.getBestRestaurantsFor(user.getUsername(), RECOMMENDED_RESTAURANTS_COUNT);
            }
            case "rating" -> mizdooni.getBestRestaurants(RECOMMENDED_RESTAURANTS_COUNT);
            default -> new Restaurant[0];
        };
    }

    //valid values for recommendBy = {userLocation, rating}
    @SneakyThrows(NotExistentUser.class)
    @GetMapping(params = {"recommendBy"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public RestaurantModel[] Recommend(@RequestParam(name="recommendBy") String recommendingMethod)
            throws MizdooniNotAuthorizedException {

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
            @RequestParam(name = "onDate") String requestDateString,
            @RequestParam(name = "requestedSeats") int requestedSeats
    ) throws NotAValidDatetime, NotExistentRestaurant {

        var requestDate = toDate(requestDateString,"onDate");
        return mizdooni.getAvailableTimes(restaurantName, requestDate, requestedSeats);
    }

}