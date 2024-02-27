package application;

import com.fasterxml.jackson.core.JsonProcessingException;
import database.Database;
import exceptions.*;
import service.Mizdooni;
import ui.ConsoleMizdooni;

public class MizdooniApplication
{
    public static void main( String[] args ) throws JsonProcessingException,
            NotExistentUser,
            NotExpectedUserRole,
            NotExistentRestaurant,
            TimeBelongsToPast,
            TableIsReserved,
            TimeIsNotRound,
            NotInWorkHour,
            NotExistentTable,
            NotExistentUser,
            NotExistentReserve,
            CancelingExpiredReserve,
            CancelingCanceledReserve,
            NotExistentUser,
            NotExistentRestaurant,
            NotExpectedUserRole,
            ScoreOutOfRange
    {
        var database = Database.CreateInMemoryDatabase();
        var mizdooniService = new Mizdooni(database);
        var ui = new ConsoleMizdooni(mizdooniService);

        ui.Start();

    }
}
