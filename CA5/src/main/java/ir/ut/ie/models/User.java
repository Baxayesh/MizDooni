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
    private String Username;

    @Column(nullable = false)
    private String Password;

    @Column(unique = true, nullable = false)
    private String Email;

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserAddress Address;

    public String getKey(){
        return Username;
    }

    public boolean Is(String username){
        return this.Username.equals(username);
    }

    public User(String username, String password, String email, String country, String city) throws InvalidAddress, InvalidUser {
        Username = username;
        Password = password;
        Email = email;
        Address = new UserAddress(this, country, city);
        ValidateUser();
    }




    void ValidateUser()
            throws InvalidUser, InvalidAddress {

        if (Username.contains(" ") || Username.contains(";") || !Pattern.matches("^[a-zA-Z0-9_]*$", Username)){
            throw new InvalidUser();
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", Email)){
            throw new InvalidUser();
        }

    }

}