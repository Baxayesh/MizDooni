package ui;

import service.Mizdooni;

import java.util.Scanner;

public class ConsoleMizdooni {

    Mizdooni Service;

    public ConsoleMizdooni(Mizdooni mizdooni){
        Service = mizdooni;
    }

    public void Start(){
        System.out.println("Mizdooni Is Up!");

        Scanner inputScanner = new Scanner(System.in);

        while (true){
            var command = inputScanner.next();

            if(command.equals("exit")){
                return;
            }

            var jsonData = inputScanner.nextLine();
            ProcessCommand(command, jsonData);
        }
    }

    void ProcessCommand(String command, String jsonData){
        System.out.printf("your command : %s your data: %s\n", command, jsonData);
        //TODO: Implement
    }
}
