package ir.ut.ie.controllers;

import ir.ut.ie.contracts.TableModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.utils.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tables")
public class TablesController extends MizdooniController {


    public record CreateTableResponse(int createdTableId){}

    @PostMapping
    CreateTableResponse CreateTable(@RequestBody Map<String, String> request)
            throws MizdooniNotAuthorizedException, FieldIsRequired, NotAValidNumber,
            NotExistentRestaurant, SeatNumNotPos, NotExistentUser {

        service.EnsureLoggedIn(UserRole.Manager);

        var restaurant = getRequiredField(request, "restaurant");
        var seatsNumber = getRequiredIntField(request, "seats");
        var managerName = service.getLoggedIn().getUsername();

        var id = service.AddTable(
                restaurant,
                managerName,
                seatsNumber
        );

        return new CreateTableResponse(id);

    }

    @GetMapping(params = {"restaurant"})
    TableModel[] GetRestaurantTables(@RequestParam(name = "restaurant") String restaurantName)
            throws NotExistentRestaurant {

        var restaurant = service.FindRestaurant(restaurantName);

        return restaurant
                .getTables()
                .stream()
                .map(TableModel::FromTableObject)
                .toArray(TableModel[]::new);
    }
}
