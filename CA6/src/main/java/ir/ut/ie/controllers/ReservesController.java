package ir.ut.ie.controllers;

import ir.ut.ie.contracts.EntityCreatedResponse;
import ir.ut.ie.contracts.ReserveModel;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Reserve;
import ir.ut.ie.utils.UserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/reserves")
public class ReservesController extends MizdooniController {

    @GetMapping
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public ReserveModel[] GetCurrentUserReserves() {

        var client = getCurrentUser();

        return Arrays.stream(mizdooni.getReserves(client.getUsername()))
                .map(ReserveModel::fromDomainObject)
                .toArray(ReserveModel[]::new);
    }

    //time format : 2007-12-31T23:59:59
    @PostMapping
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public EntityCreatedResponse ReserveTable(@RequestBody Map<String, String> request)
            throws MizdooniNotAuthorizedException, FieldIsRequired, NotAValidDatetime, NotExistentRestaurant,
            TimeBelongsToPast, NotExistentUser, NotInWorkHour, NoFreeTable, TimeIsNotRound, NotAValidNumber {


        var reservee = getCurrentUser();
        var restaurant = getRequiredField(request, "restaurant");
        var reserveTime = toDatetime(getRequiredField(request, "time"), "time");
        var seats = getRequiredIntField(request, "seats");

        var id = mizdooni.reserveATable(
                reservee.getUsername(),
                restaurant,
                reserveTime,
                seats
        );
        return new EntityCreatedResponse(id);

    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public ReserveModel GetReserveDetails(@PathVariable String id)
            throws NotAValidNumber, NotExistentReserve {

        var reservee = getCurrentUser();
        var reserveId = toInt(id, "id");

        var reserve = mizdooni.findReserve(reservee.getUsername(), reserveId);

        return ReserveModel.fromDomainObject(reserve);

    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public void CancelReserve(@PathVariable String id)
            throws NotAValidNumber, NotExistentReserve,
            CancelingExpiredReserve, CancelingCanceledReserve {

        var reservee = getCurrentUser();
        var reserveId = toInt(id, "id");

        mizdooni.cancelReserve(reservee.getUsername(), reserveId);


    }


    // date format : 2007-12-31
    @GetMapping(params = {"restaurant"})
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    public ReserveModel[] GetReservesForManager(
            @RequestParam(name = "restaurant") String restaurantName,
            @RequestParam(name = "table", required = false, defaultValue = "") String tableNumber,
            @RequestParam(name = "date" , required = false, defaultValue = "") String dateString)
            throws MizdooniNotAuthorizedException, NotExistentRestaurant,
            NotAValidNumber, NotExistentTable, NotAValidDatetime {

        var owner = getCurrentUser();

        var restaurant = mizdooni.findRestaurant(restaurantName);

        if(!restaurant.getManager().is(owner.getUsername()))
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