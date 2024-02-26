package application;

import database.Database;
import service.Mizdooni;
import ui.ConsoleMizdooni;

public class MizdooniApplication
{
    public static void main( String[] args )
    {
        var database = Database.CreateInMemoryDatabase();
        var mizdooniService = new Mizdooni(database);
        var ui = new ConsoleMizdooni(mizdooniService);

        ui.Start();

    }
}
