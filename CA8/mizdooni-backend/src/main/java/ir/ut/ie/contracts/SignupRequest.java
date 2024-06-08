package ir.ut.ie.contracts;

import jakarta.validation.constraints.*;

public record SignupRequest(

        @NotBlank
        String username,

        @NotBlank
        String password,

        @Pattern(regexp = "(client|manager)")
        String role,

        @Email
        String email,

        @Size(min = 2, max = 128)
        String country,

        @Size(min = 2, max = 128)
        String city
) {

}


