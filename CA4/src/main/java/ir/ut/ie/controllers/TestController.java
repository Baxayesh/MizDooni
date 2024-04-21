package ir.ut.ie.controllers;


import ir.ut.ie.service.Mizdooni;
import ir.ut.ie.service.MizdooniProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private Mizdooni service;

    public void setService(Mizdooni mizdooni){
        service = mizdooni;
    }

    public TestController(){
        service = MizdooniProvider.GetInstance();
    }

    @GetMapping(path = "/hello", params = {"message"})
    public String Echo(@RequestParam String message){

        return "U said: \"%s\"".formatted(message);

    }

    @GetMapping(path = "/hello")
    public String SayHello(){
        return "Hello, World!";
    }


}
