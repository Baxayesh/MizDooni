package ir.ut.ie.contracts;

import ir.ut.ie.models.RestaurantAddress;

public record RestaurantAddressModel(
        String country,
        String city,
        String street
) {
    public static RestaurantAddressModel fromDomainObject(RestaurantAddress model) {
        return new RestaurantAddressModel(
                model.getCountry(),
                model.getCity(),
                model.getStreet()
        );
    }
}
