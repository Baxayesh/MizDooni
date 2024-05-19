package ir.ut.ie.contracts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public record AddRestaurantRequest (

        @NotBlank
        String name,
        @NotBlank
        String type,

        @DateTimeFormat(pattern = "HH:mm")
        LocalTime openTime,

        @DateTimeFormat(pattern = "HH:mm")
        LocalTime closeTime,
        String description,
        @Size(min = 2, max = 128)
        String country,
        @Size(min = 2, max = 128)
        String city,
        @Size(min = 2, max = 128)
        String street,

        @URL
        String image
) {


}
