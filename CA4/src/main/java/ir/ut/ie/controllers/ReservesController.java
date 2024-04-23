package ir.ut.ie.controllers;

import ir.ut.ie.contracts.EntityCreatedResponse;
import ir.ut.ie.contracts.ReserveModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Reserve;
import ir.ut.ie.utils.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/reserves")
public class ReservesController extends MizdooniController {

    @GetMapping()
    public ReserveModel[] GetCurrentUserReserves()
            throws MizdooniNotAuthorizedException {

        try {
            service.EnsureLoggedIn(UserRole.Client);
            var client = service.getLoggedIn();

            return Arrays.stream(service.GetReserves(client.getUsername()))
                    .map(ReserveModel::FromObject)
                    .toArray(ReserveModel[]::new);
        } catch (NotExistentUser ex) {
            throw new RuntimeException(ex);
        }
    }

    //time format : 2007-12-31T23:59:59
    @PostMapping
    public EntityCreatedResponse ReserveTable(@RequestBody Map<String, String> request)
            throws MizdooniNotAuthorizedException, FieldIsRequired, NotAValidDatetime, NotExistentRestaurant,
            TimeBelongsToPast, NotExistentUser, NotInWorkHour, NoFreeTable, TimeIsNotRound {


        service.EnsureLoggedIn(UserRole.Client);
        var reservee = service.getLoggedIn();
        var restaurant = getRequiredField(request, "restaurant");
        var reserveTime = toDatetime(getRequiredField(request, "time"), "time");

        var id = service.ReserveATable(
                reservee.getUsername(),
                restaurant,
                reserveTime
        );
        return new EntityCreatedResponse(id);




    }

    @GetMapping(value = "/{id}", params = "id")
    public ReserveModel GetReserveDetails(@PathParam(value = "id") String id)
            throws MizdooniNotAuthorizedException, NotAValidNumber, NotExistentReserve {

        try {
            service.EnsureLoggedIn(UserRole.Client);
            var reservee = service.getLoggedIn();
            var reserveId = toInt(id, "id");

            var reserve = service.FindReserve(reservee.getUsername(), reserveId);

            return ReserveModel.FromObject(reserve);
        } catch (NotExistentUser ex) {
            throw new RuntimeException(ex);
        }


    }

    @DeleteMapping(value = "/{id}", params = "id")
    public void CancelReserve(@PathParam(value = "id") String id)
            throws MizdooniNotAuthorizedException, NotAValidNumber, NotExistentReserve,
            CancelingExpiredReserve, CancelingCanceledReserve {

        try {
            service.EnsureLoggedIn(UserRole.Client);
            var reservee = service.getLoggedIn();
            var reserveId = toInt(id, "id");

            service.CancelReserve(reservee.getUsername(), reserveId);

        } catch (NotExistentUser e) {
            throw new MizdooniNotAuthorizedException();
        }

    }


    // date format : 2007-12-31
    @GetMapping(params = {"restaurant", "table", "date"})
    public ReserveModel[] GetReservesForManager(
            @RequestParam(name = "restaurant") String restaurantName,
            @RequestParam(name = "table", required = false, defaultValue = "") String tableNumber,
            @RequestParam(name = "date" , required = false, defaultValue = "") String dateString)
            throws MizdooniNotAuthorizedException, NotExistentRestaurant,
            NotAValidNumber, NotExistentTable, NotAValidDatetime {

        service.EnsureLoggedIn(UserRole.Manager);
        var owner = service.getLoggedIn();

        var restaurant = service.FindRestaurant(restaurantName);

        if(!owner.Is(restaurant.getManagerUsername()))
            throw new MizdooniNotAuthorizedException();

        Stream<Reserve> reserves;

        if(tableNumber.isBlank()){
            reserves = restaurant.getTables().stream().flatMap(table -> table.getReserves().stream());
        }else{
            var table = toInt(tableNumber, "table");
            reserves = restaurant.getTable(table).getReserves().stream();
        }

        if(!dateString.isBlank()){
            var filterDate = toDate(dateString, "date");
            reserves = reserves.filter(reserve -> reserve.GetReserveTime().toLocalDate().equals(filterDate));
        }

        return reserves.map(ReserveModel::FromObject).toArray(ReserveModel[]::new);
    }
}
