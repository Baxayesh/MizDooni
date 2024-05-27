package ir.ut.ie.controllers;

import ir.ut.ie.contracts.*;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Restaurant;
import ir.ut.ie.utils.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@RestController
public class RestaurantsController extends MizdooniController {

    static final int RECOMMENDED_RESTAURANTS_COUNT = 6;

    @SneakyThrows(NotExistentUser.class)
    @PostMapping("/restaurants")
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

    @GetMapping("/restaurants")
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


    @GetMapping("/search")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public PagedResponse<RestaurantModel> SearchByType(
            @RequestParam(name="name", required = false) String name,
            @RequestParam(name="location", required = false) String location,
            @RequestParam(name="type", required = false) String type,
            @Positive @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @Positive @Max(100) @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ) {
        var results = new Restaurant[0];

        if(!name.isBlank()){
            results = mizdooni.searchRestaurantByName(name, offset, limit);
        }else if(!location.isBlank()){
            results = mizdooni.searchRestaurantByLocation(location, offset, limit);
        }else if (!type.isBlank()){
            results = mizdooni.searchRestaurantByType(type, offset, limit);
        }

        return pageResults(results, offset, limit);
    }

    @SneakyThrows(NotExistentUser.class)
    @GetMapping("/recommend")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public RestaurantModel[] Recommend(
        @RequestParam(name="recommendBy", required = false, defaultValue = "false") Boolean inUserLocation
    ) {

        Restaurant[] restaurants;

        if(inUserLocation){
            var user = getCurrentUser();
            restaurants = mizdooni.getBestRestaurantsFor(user.getUsername(), RECOMMENDED_RESTAURANTS_COUNT);

        }else{
            restaurants = mizdooni.getBestRestaurants(RECOMMENDED_RESTAURANTS_COUNT);
        }

        return Arrays.stream(restaurants).map(RestaurantModel::fromDomainObject).toArray(RestaurantModel[]::new);
    }

    @GetMapping(path = "/restaurants/{name}")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public RestaurantModel GetRestaurant(
            @PathVariable(name = "name") String restaurantName
    ) throws NotExistentRestaurant {

        return RestaurantModel.fromDomainObject(mizdooni.findRestaurant(restaurantName));
    }

    @GetMapping(path = "/restaurants/{name}/availableTimeSlots", params={"onDate", "requestedSeats"})
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public LocalTime[] GetAvailableReserveSlots(
            @PathVariable(name = "name") String restaurantName,
            @FutureOrPresent @RequestParam(name = "onDate") LocalDate requestDate,
            @Positive @RequestParam(name = "requestedSeats") int requestedSeats
    ) throws NotExistentRestaurant {

        return mizdooni.getAvailableTimes(restaurantName, requestDate, requestedSeats);
    }

}