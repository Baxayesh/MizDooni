package ir.ut.ie.contracts;

import ir.ut.ie.models.UserAddress;
import jakarta.validation.constraints.NotBlank;

public record UserAddressModel(
        String country,
        String city
) {

    public static UserAddressModel fromDomainObject(UserAddress model) {

        return new UserAddressModel(
                model.getCountry(),
                model.getCity()
        );
    }

}

