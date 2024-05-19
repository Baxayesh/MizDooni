package ir.ut.ie.contracts;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ReserveTableRequest(

        @NotBlank
        String restaurant,

        @Future
        LocalDateTime time,

        @Positive
        int seats
) {

}
