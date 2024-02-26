package service;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testUtils.MizdooniStubHelper;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Table Reservation Tests")
public class TableReservationTests {

    MizdooniStubHelper stub;

    @BeforeEach
    void SetupRequirements() {
        stub = new MizdooniStubHelper();
    }

    LocalDateTime CreateReserveTime(int daysFromToday, int hour, int minute){
        var now = LocalDateTime.now();
        var reservationDay = now.toLocalDate().plusDays(daysFromToday);
        var reservationTime = LocalTime.of(hour,minute);

        return LocalDateTime.of(
                reservationDay,
                reservationTime
        );
    }

    LocalDateTime CreateValidReserveTime(){
        // assuming every restaurant is open at 12 o'clock
        return CreateReserveTime(1,12, 0);
    }

    @Test
    void GIVEN_normalMizdooni_WHEN_doReserveWithNotExistentUsername_THEN_shouldThrowNotExistentUser() {

        var restaurant = "restaurant";
        var table = 1;
        var username = "not_existent_username";
        var reserveTime = CreateValidReserveTime();
        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousTable(restaurant, table);

        assertThrows(
                NotExistentUser.class,
                () ->  stub.Mizdooni().ReserveATable(
                        username,
                        restaurant,
                        table,
                        reserveTime
                )
        );
    }


    @Test
    void GIVEN_normalMizdooni_WHEN_doReserveWithManagerUsername_THEN_shouldThrowNotExpectedUserRole() {

        var restaurant = "restaurant";
        var table = 1;
        var username = "manager";
        var reserveTime = CreateValidReserveTime();
        stub.AddAnonymousManager(username);
        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousTable(restaurant, table);

        assertThrows(
                NotExpectedUserRole.class,
                () ->  stub.Mizdooni().ReserveATable(
                        username,
                        restaurant,
                        table,
                        reserveTime
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_doReserveWithNotRoundReserveTime_THEN_shouldThrowTimeIsNotRound() {

        var restaurant = "restaurant";
        var table = 1;
        var username = "user";
        var reserveTime = CreateReserveTime(1,12, 35);
        stub.AddAnonymousCustomer(username);
        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousTable(restaurant, table);

        assertThrows(
                TimeIsNotRound.class,
                () ->  stub.Mizdooni().ReserveATable(
                        username,
                        restaurant,
                        table,
                        reserveTime
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_doReserveWithForNotExistentRestaurant_THEN_shouldThrowNotExistentRestaurant() {

        var restaurant = "restaurant";
        var table = 1;
        var username = "user";
        var reserveTime = CreateValidReserveTime();
        stub.AddAnonymousCustomer(username);

        assertThrows(
                NotExistentRestaurant.class,
                () ->  stub.Mizdooni().ReserveATable(
                        username,
                        restaurant,
                        table,
                        reserveTime
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_doReserveWithForNotExistentTable_THEN_shouldThrowNotExistentTable() {

        var restaurant = "restaurant";
        var table = 1;
        var username = "user";
        var reserveTime = CreateValidReserveTime();
        stub.AddAnonymousCustomer(username);
        stub.AddAnonymousRestaurant(restaurant);

        assertThrows(
                NotExistentTable.class,
                () ->  stub.Mizdooni().ReserveATable(
                        username,
                        restaurant,
                        table,
                        reserveTime
                )
        );

    }


    @Test
    void GIVEN_normalMizdooni_WHEN_ReserveAPreviouslyReservedTable_THEN_shouldThrowTableIsReserved() {

        var restaurant = "restaurant";
        var table = 1;
        var username = "user";
        var reserveTime = CreateValidReserveTime();
        stub.AddAnonymousCustomer(username);
        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousTable(restaurant, table);
        stub.AddPreviousReserve("someone_else", restaurant, table, reserveTime);

        assertThrows(
                TableIsReserved.class,
                () ->  stub.Mizdooni().ReserveATable(
                        username,
                        restaurant,
                        table,
                        reserveTime
                )
        );

    }


    @Test
    void GIVEN_normalMizdooni_WHEN_ReserveATableInPast_THEN_shouldThrowTimeBelongsToPast() {

        var restaurant = "restaurant";
        var table = 1;
        var username = "user";
        var reserveTime = CreateReserveTime(-1, 12, 0);
        stub.AddAnonymousCustomer(username);
        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousTable(restaurant, table);

        assertThrows(
                TimeBelongsToPast.class,
                () ->  stub.Mizdooni().ReserveATable(
                        username,
                        restaurant,
                        table,
                        reserveTime
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_ReserveATableWhenRestaurantIsClosed_THEN_shouldThrowNotInWorkHour() {

        var restaurant = "restaurant";
        var table = 1;
        var username = "user";
        var reserveTime = CreateReserveTime(1, 19, 0);
        stub.AddAnonymousCustomer(username);
        stub.AddAnonymousRestaurant(restaurant, 8, 18);
        stub.AddAnonymousTable(restaurant, table);

        assertThrows(
                NotInWorkHour.class,
                () ->  stub.Mizdooni().ReserveATable(
                        username,
                        restaurant,
                        table,
                        reserveTime
                )
        );

    }

    @Test
    void GIVEN_normalMizdooni_WHEN_MakingAValidReservation_THEN_ReserveRecordShouldBeSaved()
            throws
            NotExistentRestaurant,
            TimeBelongsToPast,
            NotExpectedUserRole,
            TableIsReserved,
            NotExistentUser,
            TimeIsNotRound,
            NotInWorkHour,
            NotExistentTable
    {

        var restaurant = "restaurant";
        var table = 1;
        var username = "user";
        var reserveTime = CreateValidReserveTime();
        stub.AddAnonymousCustomer(username);
        stub.AddAnonymousRestaurant(restaurant);
        stub.AddAnonymousTable(restaurant, table);

        var reserve =
                stub.Mizdooni().ReserveATable(
                    username,
                    restaurant,
                    table,
                    reserveTime
                );

        stub.AssertReserveRegistered(
                reserve.getReserveNumber(),
                username,
                restaurant,
                table,
                reserveTime
        );

    }

}


