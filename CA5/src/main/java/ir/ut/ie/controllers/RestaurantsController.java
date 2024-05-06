package ir.ut.ie.controllers;

import ir.ut.ie.contracts.*;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.utils.UserRole;
import lombok.SneakyThrows;
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
    public void AddRestaurant(@RequestBody Map<String, String> request) throws FieldIsRequired,
            NotAValidDatetime, RestaurantAlreadyExists, MizdooniNotAuthorizedException, InvalidAddress {

        service.ensureLoggedIn(UserRole.Manager);
        var owner = service.getLoggedIn();

        service.addRestaurant(
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
    public RestaurantModel[] GetManagerRestaurants() throws MizdooniNotAuthorizedException {

        service.ensureLoggedIn(UserRole.Manager);
        var owner = service.getLoggedIn();

        return Arrays.stream(service.getRestaurantsFor(owner.getUsername()))
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
    public PagedResponse<RestaurantModel> SearchByName(
            @RequestParam(name="name") String name,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) throws MizdooniNotAuthorizedException {
        service.ensureLoggedIn();
        return pageResults(service.searchRestaurantByName(name), offset, limit);
    }

    @GetMapping(params = {"type"})
    public PagedResponse<RestaurantModel> SearchByType(
            @RequestParam(name="type") String type,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) throws MizdooniNotAuthorizedException {
        service.ensureLoggedIn();
        return pageResults(service.searchRestaurantByType(type), offset, limit);
    }

    @GetMapping(params = {"location"})
    public PagedResponse<RestaurantModel> SearchByLocation(
            @RequestParam(name="location") String location,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) throws MizdooniNotAuthorizedException {
        service.ensureLoggedIn();
        return pageResults(service.searchRestaurantByLocation(location), offset, limit);
    }

    Restaurant[] FetchRecommendations(String recommendingMethod) throws MizdooniNotAuthorizedException {
        return switch (recommendingMethod) {
            case "userLocation" -> {
                var user = service.getLoggedIn();
                yield service.getBestRestaurants(user.getUserAddress().city(), RECOMMENDED_RESTAURANTS_COUNT);
            }
            case "rating" -> service.getBestRestaurants(RECOMMENDED_RESTAURANTS_COUNT);
            default -> new Restaurant[0];
        };
    }

    //valid values for recommendBy = {userLocation, rating}
    @GetMapping(params = {"recommendBy"})
    public RestaurantModel[] Recommend(@RequestParam(name="recommendBy") String recommendingMethod)
            throws MizdooniNotAuthorizedException {

        service.ensureLoggedIn(UserRole.Client);
        var restaurants = FetchRecommendations(recommendingMethod);
        return Arrays.stream(restaurants).map(RestaurantModel::fromDomainObject).toArray(RestaurantModel[]::new);
    }
    @GetMapping(path = "/{name}")
    public RestaurantModel GetRestaurant(
            @PathVariable(name = "name") String restaurantName
    ) throws NotExistentRestaurant, MizdooniNotAuthorizedException {

        service.ensureLoggedIn();
        return RestaurantModel.fromDomainObject(service.findRestaurant(restaurantName));
    }

    @GetMapping(path = "/{name}/availableTimeSlots", params={"onDate", "requestedSeats"})
    public LocalTime[] GetAvailableReserveSlots(
            @PathVariable(name = "name") String restaurantName,
            @RequestParam(name = "onDate") String requestDateString,
            @RequestParam(name = "requestedSeats") int requestedSeats
    ) throws NotAValidDatetime, NotExistentRestaurant, MizdooniNotAuthorizedException {

        service.ensureLoggedIn(UserRole.Client);
        var requestDate = toDate(requestDateString,"onDate");
        return service.getAvailableTables(restaurantName, requestDate, requestedSeats);
    }

}