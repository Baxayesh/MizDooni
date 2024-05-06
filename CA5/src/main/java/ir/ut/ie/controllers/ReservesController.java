package ir.ut.ie.controllers;

import ir.ut.ie.contracts.EntityCreatedResponse;
import ir.ut.ie.contracts.ReserveModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Reserve;
import ir.ut.ie.utils.UserRole;
import lombok.SneakyThrows;
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

        service.ensureLoggedIn(UserRole.Client);
        var client = service.getLoggedIn();

        return Arrays.stream(service.getReserves(client.getUsername()))
                .map(ReserveModel::fromDomainObject)
                .toArray(ReserveModel[]::new);
    }

    //time format : 2007-12-31T23:59:59
    @PostMapping
    public EntityCreatedResponse ReserveTable(@RequestBody Map<String, String> request)
            throws MizdooniNotAuthorizedException, FieldIsRequired, NotAValidDatetime, NotExistentRestaurant,
            TimeBelongsToPast, NotExistentUser, NotInWorkHour, NoFreeTable, TimeIsNotRound, NotAValidNumber {


        service.ensureLoggedIn(UserRole.Client);
        var reservee = service.getLoggedIn();
        var restaurant = getRequiredField(request, "restaurant");
        var reserveTime = toDatetime(getRequiredField(request, "time"), "time");
        var seats = getRequiredIntField(request, "seats");

        var id = service.reserveATable(
                reservee.getUsername(),
                restaurant,
                reserveTime,
                seats
        );
        return new EntityCreatedResponse(id);

    }

    @GetMapping(value = "/{id}")
    @SneakyThrows(NotExistentUser.class)
    public ReserveModel GetReserveDetails(@PathVariable String id)
            throws MizdooniNotAuthorizedException, NotAValidNumber, NotExistentReserve {

        service.ensureLoggedIn(UserRole.Client);
        var reservee = service.getLoggedIn();
        var reserveId = toInt(id, "id");

        var reserve = service.findReserve(reservee.getUsername(), reserveId);

        return ReserveModel.fromDomainObject(reserve);

    }

    @DeleteMapping(value = "/{id}")
    @SneakyThrows(NotExistentUser.class)
    public void CancelReserve(@PathVariable String id)
            throws MizdooniNotAuthorizedException, NotAValidNumber, NotExistentReserve,
            CancelingExpiredReserve, CancelingCanceledReserve {

        service.ensureLoggedIn(UserRole.Client);
        var reservee = service.getLoggedIn();
        var reserveId = toInt(id, "id");

        service.cancelReserve(reservee.getUsername(), reserveId);


    }


    // date format : 2007-12-31
    @GetMapping(params = {"restaurant"})
    public ReserveModel[] GetReservesForManager(
            @RequestParam(name = "restaurant") String restaurantName,
            @RequestParam(name = "table", required = false, defaultValue = "") String tableNumber,
            @RequestParam(name = "date" , required = false, defaultValue = "") String dateString)
            throws MizdooniNotAuthorizedException, NotExistentRestaurant,
            NotAValidNumber, NotExistentTable, NotAValidDatetime {

        service.ensureLoggedIn(UserRole.Manager);
        var owner = service.getLoggedIn();

        var restaurant = service.findRestaurant(restaurantName);

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

        return reserves.map(ReserveModel::fromDomainObject).toArray(ReserveModel[]::new);
    }
}