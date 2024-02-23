package application;

import service.Mizdooni;
import ui.ConsoleMizdooni;

public class MizdooniApplication
{
    public static void main( String[] args )
    {

        var mizdooniService = new Mizdooni();
        var ui = new ConsoleMizdooni(mizdooniService);

        ui.Start();

    }
}
