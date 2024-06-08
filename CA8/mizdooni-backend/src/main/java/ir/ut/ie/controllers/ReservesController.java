package ir.ut.ie.controllers;

import co.elastic.apm.api.CaptureTransaction;
import ir.ut.ie.contracts.EntityCreatedResponse;
import ir.ut.ie.contracts.ReserveModel;
import ir.ut.ie.contracts.ReserveTableRequest;
import ir.ut.ie.exceptions.*;
import ir.ut.ie.models.Reserve;
import ir.ut.ie.utils.UserRole;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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


    @PostMapping
    @CaptureTransaction("Reserve a Table")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    @SneakyThrows(NotExistentUser.class)
    public EntityCreatedResponse ReserveTable(@Valid @RequestBody ReserveTableRequest request)
            throws NotExistentRestaurant, NotInWorkHour, NoFreeTable, TimeIsNotRound {

        var reservee = getCurrentUser();

        var id = mizdooni.reserveATable(
                reservee.getUsername(),
                request.restaurant(),
                request.time(),
                request.seats()
        );

        observabilityService.addReserve();
        return new EntityCreatedResponse(id);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public ReserveModel GetReserveDetails(@PathVariable(name = "id") Integer reserveId)
            throws NotExistentReserve {

        var reservee = getCurrentUser();

        var reserve = mizdooni.findReserve(reservee.getUsername(), reserveId);

        return ReserveModel.fromDomainObject(reserve);

    }

    @DeleteMapping(value = "/{id}")
    @CaptureTransaction("Cancel Reserve")
    @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
    public void CancelReserve(@PathVariable(name = "id") Integer reserveId)
            throws NotExistentReserve, CancelingExpiredReserve, CancelingCanceledReserve {

        var reservee = getCurrentUser();

        mizdooni.cancelReserve(reservee.getUsername(), reserveId);
        observabilityService.addCanceledReserve();
    }

    @GetMapping(params = {"restaurant"})
    @PreAuthorize(UserRole.SHOULD_BE_MANAGER)
    public ReserveModel[] GetReservesForManager(
            @RequestParam(name = "restaurant", required = false, defaultValue = "") String restaurantName,
            @RequestParam(name = "table", required = false, defaultValue = "") String tableNumber,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(name = "date" , required = false, defaultValue = "") String dateString)
            throws NotExistentRestaurant, NotAValidNumber, NotExistentTable, NotAValidDatetime {

        var owner = getCurrentUser();

        var restaurant = mizdooni.findRestaurant(restaurantName);

        if(!restaurant.getManager().is(owner.getUsername()))
            throw new NotExistentRestaurant();

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