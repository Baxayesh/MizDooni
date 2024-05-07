package ir.ut.ie.controllers;

import ir.ut.ie.contracts.EntityCreatedResponse;
import ir.ut.ie.contracts.TableModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.utils.UserRole;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tables")
public class TablesController extends MizdooniController {

    @PostMapping
    @SneakyThrows(NotExistentUser.class)
    EntityCreatedResponse CreateTable(@RequestBody Map<String, String> request)
            throws MizdooniNotAuthorizedException, FieldIsRequired, NotAValidNumber,
            NotExistentRestaurant, SeatNumNotPos {

        service.ensureLoggedIn(UserRole.Manager);

        var restaurant = getRequiredField(request, "restaurant");
        var seatsNumber = getRequiredIntField(request, "seats");
        var managerName = service.getLoggedIn().getUsername();

        var id = service.addTable(
                restaurant,
                managerName,
                seatsNumber
        );

        return new EntityCreatedResponse(id);

    }

    @GetMapping(params = {"restaurant"})
    TableModel[] GetRestaurantTables(@RequestParam(name = "restaurant") String restaurantName)
            throws NotExistentRestaurant, MizdooniNotAuthorizedException {

        service.ensureLoggedIn(UserRole.Manager);
        var manager = service.getLoggedIn();

        var restaurant = service.findRestaurant(restaurantName);

        if(!manager.Is(restaurant.getManager().getUsername()))
            throw new MizdooniNotAuthorizedException();

        return restaurant
                .getTables()
                .stream()
                .map(TableModel::fromDomainObject)
                .toArray(TableModel[]::new);
    }
}