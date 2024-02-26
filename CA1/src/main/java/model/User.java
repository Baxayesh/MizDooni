package model;

import lombok.Getter;
import lombok.Setter;
import utils.UserRole;


@Getter
@Setter
public class User extends EntityModel<String> {

    //fields: username password, email, address, role
    private UserRole Role;

    public User(String username, UserRole role) {
        super(username);
        Role = role;
    }

    public String getUsername(){
        return super.getKey();
    }

    public boolean RoleIs(UserRole desiredRole) {
        return Role == desiredRole;
    }

}