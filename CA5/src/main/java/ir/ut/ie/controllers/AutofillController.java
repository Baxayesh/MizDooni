package ir.ut.ie.controllers;

import ir.ut.ie.utils.LocationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutofillController extends MizdooniController {

        @GetMapping("/locations")
        public LocationModel[] GetLocations(){
                return service.getUsedLocations();
        }

        @GetMapping("/foodTypes")
        public String[] GetFoodTypes(){
                return service.getUsedFoodTypes();
        }

}