package model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import utils.UserRole;


@Getter
@NoArgsConstructor
@Setter
public class User {

    private String Username;
    private UserRole Role;

    public boolean Is(UserRole desiredRole) {
        return Role == desiredRole;
    }

    public boolean Is(String username){
        return Username.equalsIgnoreCase(username);
    }

    //fields: username password, email, address, role

}