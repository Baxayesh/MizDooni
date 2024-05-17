package ir.ut.ie.database;

import ir.ut.ie.exceptions.NotExistentUser;
import ir.ut.ie.exceptions.UserAlreadyExits;
import ir.ut.ie.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Repository
@Setter
@Getter
@NoArgsConstructor
public class UserRepository implements IUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(User user) throws UserAlreadyExits {
        if(exists(user.getUsername()))
            throw new UserAlreadyExits();

        entityManager.persist(user);
        entityManager.persist(user.getAddress());
    }

    @Override
    public User get(String username) throws NotExistentUser {
        var user = entityManager.find(User.class, username);

        if (user == null) {
            throw new NotExistentUser();
        }

        return user;
    }

    @Override
    public boolean exists(String username) {
        var obj = entityManager.find(User.class, username);
        return obj != null;
    }
}
