package ir.ut.ie.models;

import ir.ut.ie.utils.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_ROLE")
public abstract class User implements Serializable, UserDetails {

    @Id
    private String Username;

    @Column(nullable = false)
    private String EncodedPassword;

    @Column(unique = true, nullable = false)
    private String Email;

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserAddress Address;

    public boolean is(String username){
        return this.Username.equals(username);
    }

    public User(String username, String encodedPassword, String email, String country, String city)  {
        Username = username;
        EncodedPassword = encodedPassword;
        Email = email;
        Address = new UserAddress(this, country, city);
    }

    public abstract UserRole getRole();

    @Override
    public String getPassword(){
        return EncodedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}