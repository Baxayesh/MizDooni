package ir.ut.ie.controllers;

import ir.ut.ie.utils.LocationModel;
import ir.ut.ie.utils.UserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutofillController extends MizdooniController {

        @GetMapping("/locations")
        @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
        public LocationModel[] GetLocations(){
                return mizdooni.getUsedLocations();
        }


        @GetMapping("/foodTypes")
        @PreAuthorize(UserRole.SHOULD_BE_CLIENT)
        public String[] GetFoodTypes(){
                return mizdooni.getUsedFoodTypes();
        }

}