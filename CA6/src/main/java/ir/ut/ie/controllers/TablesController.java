package ir.ut.ie.controllers;

import ir.ut.ie.contracts.CreateTableRequest;
import ir.ut.ie.contracts.EntityCreatedResponse;
import ir.ut.ie.contracts.TableModel;
import ir.ut.ie.exceptions.NotExistentRestaurant;
import ir.ut.ie.utils.UserRole;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tables")
public class TablesController extends MizdooniController {

    @PostMapping
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    EntityCreatedResponse CreateTable(@Valid @RequestBody CreateTableRequest request)
            throws NotExistentRestaurant {

        var managerName = getCurrentUser().getUsername();

        var id = mizdooni.addTable(
                request.restaurant(),
                managerName,
                request.seats()
        );

        return new EntityCreatedResponse(id);

    }

    @GetMapping(params = {"restaurant"})
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    TableModel[] GetRestaurantTables(@RequestParam(name = "restaurant") String restaurantName)
            throws NotExistentRestaurant {

        var manager = getCurrentUser();

        var restaurant = mizdooni.findRestaurant(restaurantName);

        if(!restaurant.getManager().is(manager.getUsername()))
            throw new NotExistentRestaurant();

        return restaurant
                .getTables()
                .stream()
                .map(TableModel::fromDomainObject)
                .toArray(TableModel[]::new);
    }
}