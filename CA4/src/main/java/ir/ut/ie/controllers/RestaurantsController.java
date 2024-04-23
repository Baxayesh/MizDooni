package ir.ut.ie.controllers;

import ir.ut.ie.contracts.PagedResponse;
import ir.ut.ie.contracts.RestaurantModel;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController extends MizdooniController {

    @SneakyThrows(ExecutionControl.NotImplementedException.class)
    @PostMapping
    public void AddRestaurant(@RequestBody Map<String, String> request){
        throw new ExecutionControl.NotImplementedException("");
    }

    @SneakyThrows(ExecutionControl.NotImplementedException.class)
    @GetMapping
    public RestaurantModel[] GetManagerRestaurants(){
        throw new ExecutionControl.NotImplementedException("");
    }

    @SneakyThrows(ExecutionControl.NotImplementedException.class)
    //valid values for by = {type, location, name}
    @GetMapping(params = {"q","by"})
    public PagedResponse<RestaurantModel> Search(
            @RequestParam(name="q") String query,
            @RequestParam(name="by") String searchMethod,
            @RequestParam(name="offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name="limit", required = false, defaultValue = "5") int limit
    ){
        throw new ExecutionControl.NotImplementedException("");
    }

    @SneakyThrows(ExecutionControl.NotImplementedException.class)
    //valid values for recommendBy = {userLocation, bestRating}
    @GetMapping(params = {"recommendBy"})
    public RestaurantModel[] Recommend(@RequestParam(name="recommendBy") String recommendingMethod){
        throw new ExecutionControl.NotImplementedException("");
    }

    @SneakyThrows(ExecutionControl.NotImplementedException.class)
    @GetMapping(path = "/{name}", params={"onDate", "requestedSeats"})
    public LocalTime[] GetAvailableReserveSlots(
            @PathVariable(name = "name") String restaurantName,
            @RequestParam(name = "onDate") String requestDateString,
            @RequestParam(name = "requestedSeats") int requestedSeats
    ){
        throw new ExecutionControl.NotImplementedException("");

    }

}