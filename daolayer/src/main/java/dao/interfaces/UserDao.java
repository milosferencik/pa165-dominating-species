package dao.interfaces;

import dao.entities.User;

import java.util.List;

/**
 * @author Ferencik on 25/03/2020.
 */
public interface UserDao {
    /**
     * Persists user into the database
     * @param user instance of user
     */
    void createUser(User user);

    /**
     * Gets all users from the database
     * @return List of all users retrieved from database, null if none
     */
    List<User> getAllUsers();

    /**
     * Finds a user by id in the database and gets it
     * @param id is user id
     * @return user retrieved from database
     */
    User getUser(Long id);

    /**
     * Finds user by id and updates it with new attributes
     * @param user user already stored in the database which shall be updated
     */
    void updateUser(User user);

    /**
     * Finds user by id and deletes it from the database
     * @param id of user already stored in the database which shall be deleted
     */
    void deleteUser(Long id);

    /**
     * Finds a user by email in the database
     * @param email is email of user
     * @return user retrieved from database
     */
    User getUserByEmail(String email);
}
