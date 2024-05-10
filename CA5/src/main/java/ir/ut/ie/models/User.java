package ir.ut.ie.models;

import ir.ut.ie.exceptions.InvalidAddress;
import ir.ut.ie.exceptions.InvalidUser;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_ROLE")
public abstract class User implements Serializable {

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "address")
    private UserAddress address;

    public String getKey(){
        return username;
    }

    public boolean Is(String username){
        return this.username.equals(username);
    }

    public User(String username, String password, String email, String country, String city) throws InvalidAddress, InvalidUser {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = new UserAddress(this, country, city);
        ValidateUser();
    }




    void ValidateUser()
            throws InvalidUser, InvalidAddress {

        if (username.contains(" ") || username.contains(";") || !Pattern.matches("^[a-zA-Z0-9_]*$", username)){
            throw new InvalidUser();
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)){
            throw new InvalidUser();
        }

    }

}