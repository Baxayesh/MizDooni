package ir.ut.ie.controllers;

import ir.ut.ie.contracts.EntityCreatedResponse;
import ir.ut.ie.contracts.TableModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.utils.UserRole;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tables")
public class TablesController extends MizdooniController {

    @PostMapping
    @SneakyThrows(NotExistentUser.class)
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    EntityCreatedResponse CreateTable(@RequestBody Map<String, String> request)
            throws MizdooniNotAuthorizedException, FieldIsRequired, NotAValidNumber,
            NotExistentRestaurant, SeatNumNotPos {


        var restaurant = getRequiredField(request, "restaurant");
        var seatsNumber = getRequiredIntField(request, "seats");
        var managerName = getCurrentUser().getUsername();

        var id = mizdooni.addTable(
                restaurant,
                managerName,
                seatsNumber
        );

        return new EntityCreatedResponse(id);

    }

    @GetMapping(params = {"restaurant"})
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    TableModel[] GetRestaurantTables(@RequestParam(name = "restaurant") String restaurantName)
            throws NotExistentRestaurant, MizdooniNotAuthorizedException {

        var manager = getCurrentUser();

        var restaurant = mizdooni.findRestaurant(restaurantName);

        if(!restaurant.getManager().is(manager.getUsername()))
            throw new MizdooniNotAuthorizedException();

        return restaurant
                .getTables()
                .stream()
                .map(TableModel::fromDomainObject)
                .toArray(TableModel[]::new);
    }
}