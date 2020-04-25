package cz.muni.fi.services.interfaces;

import dao.entities.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author Kostka on 25/04/2020.
 */
public interface UserService {

    /**
     * Creates new user in the database
     * @param user to be created
     */
    void createUser(User user) throws DataAccessException;

    /**
     * Gets all users from the database
     * @return List of all users from database, null if none
     */
    List<User> getAllUsers() throws DataAccessException;

    /**
     * Gets a user with given id from the database
     * @param id is id of the user
     * @return user retrieved from database
     */
    User getUser(Long id) throws DataAccessException;

    /**
     * Updates user in the database
     * @param user user to be updated
     */
    void updateUser(User user) throws DataAccessException;

    /**
     * Deletes user from the database
     * @param id of user to be deleted
     */
    void deleteUser(Long id) throws DataAccessException;

    /**
     * Finds a user by email in the database
     * @param email is email of user
     * @return user retrieved from database
     */
    User getUserByEmail(String email) throws DataAccessException;

    /**
     * Checks whether given user is admin
     * @param id of user who be checked
     * @return true if giver user is admin, false if not
     */
    boolean isAdmin(Long id) throws DataAccessException;
}
