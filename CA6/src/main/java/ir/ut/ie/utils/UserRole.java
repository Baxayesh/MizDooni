package ir.ut.ie.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;


@Getter
@RequiredArgsConstructor
public enum UserRole {

    Manager("MANAGER"),
    Client("CLIENT"),

    ;

    private final String Name;

    public String getRoleName(){
        return "ROLE_" + Name;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRoleName()));
    }

    public static final String SHOULD_BE_MANAGER = "hasRole('MANAGER')";
    public static final String SHOULD_BE_CLIENT = "hasRole('CLIENT')";
}
