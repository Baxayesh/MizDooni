package ir.ut.ie.contracts;

import ir.ut.ie.models.User;
import ir.ut.ie.utils.UserRole;

public record UserModel(

        String username,
        String email,
        UserAddressModel address,
        UserRole role
) {

    public static UserModel fromDomainObject(User model){
        return new UserModel(
                model.getUsername(),
                model.getEmail(),
                UserAddressModel.fromDomainObject(model.getAddress()),
                model.getRole()
        );
    }
}

