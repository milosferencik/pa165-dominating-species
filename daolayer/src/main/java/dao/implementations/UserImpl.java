package dao.implementations;

import dao.entities.User;
import dao.interfaces.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        entityManager.persist(user);
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT e FROM User e", User.class).getResultList();
    }

    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public void deleteUser(User user) {
        entityManager.remove(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return entityManager.createQuery("select u from User u where u.email=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
