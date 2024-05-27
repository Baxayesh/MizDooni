package ir.ut.ie.contracts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateTableRequest(
        @NotBlank
        String restaurant,

        @Positive
        int seats
) {
}
