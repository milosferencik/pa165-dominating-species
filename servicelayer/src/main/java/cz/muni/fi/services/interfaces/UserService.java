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
    void createUser(User user, String password) throws DataAccessException;

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

    /**
     * Try to authenticate a user
     * @param user a given user
     * @param password a given password
     * @return true if password for given user was correct, false otherwise
     */
    boolean authenticate(User user, String password) throws DataAccessException;

    /**
     * Change user's password
     * @param user given user
     * @param password current password of user
     * @param newPassword new password
     * @return true if password was changed successfully, false otherwise
     */
    public boolean changePassword(User user, String password, String newPassword);

    void grantPermission(User userD, boolean setAdmin);
}
