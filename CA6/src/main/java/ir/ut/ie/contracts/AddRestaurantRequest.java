package ir.ut.ie.contracts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @JsonDeserialize(using = LocalTimeDeserializer.class)
        LocalTime openTime,
        @DateTimeFormat(pattern = "HH:mm")
        @JsonDeserialize(using = LocalTimeDeserializer.class)
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
