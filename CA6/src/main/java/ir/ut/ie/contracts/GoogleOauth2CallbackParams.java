package ir.ut.ie.contracts;

import jakarta.validation.constraints.NotBlank;

public record GoogleOauth2CallbackParams(
        @NotBlank
        String userCode
) {

}
