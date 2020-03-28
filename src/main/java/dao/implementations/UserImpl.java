package dao.implementations;

import dao.entities.User;
import dao.interfaces.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ferencik on 25/03/2020.
 */
@Repository
public class UserImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createUser(User user) {
        if (user == null)
        {
            throw new IllegalArgumentException("User can not be null");
        }
        entityManager.persist(user);
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT e FROM User e", User.class).getResultList();
    }

    public User getUser(Long id) {
        if (id == null)
        {
            throw new IllegalArgumentException("Id can not be null");
        }
        return entityManager.find(User.class, id);
    }

    public void updateUser(User user) {
        if (user == null)
        {
            throw new IllegalArgumentException("User can not be null");
        }

        /*if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }

        if (user.getSurname() == null || user.getSurname().isEmpty()) {
            throw new IllegalArgumentException("Surname cannot be empty or null");
        }

        if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty() ) {
            throw new IllegalArgumentException("Password cannot be empty or null");
        }

        if (user.getId() == null) {
            throw new IllegalArgumentException("Id cannot be empty or null");
        }*/
        entityManager.merge(user);
    }

    public void deleteUser(User user) {
        if (user == null)
        {
            throw new IllegalArgumentException("User can not be null");
        }
        entityManager.remove(user);
    }
}
