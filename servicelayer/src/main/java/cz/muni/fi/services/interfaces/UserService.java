package cz.muni.fi.services.interfaces;

import dao.entities.User;

import java.util.List;

public interface UserService {

    /**
     * Creates new user in the database
     * @param user to be created
     */
    void createUser(User user);

    /**
     * Gets all users from the database
     * @return List of all users from database, null if none
     */
    List<User> getAllUsers();

    /**
     * Gets a user with given id from the database
     * @param id is id of the user
     * @return user retrieved from database
     */
    User getUser(Long id);

    /**
     * Updates user in the database
     * @param user user to be updated
     */
    void updateUser(User user);

    /**
     * Deletes user from the database
     * @param user user to be deleted
     */
    void deleteUser(User user);

    /**
     * Finds a user by email in the database
     * @param email is email of user
     * @return user retrieved from database
     */
    User getUserByEmail(String email);

    /**
     * Checks whether given user is admin
     * @param user to be checked
     * @return true if giver user is admin, false if not
     */
    boolean isAdmin(User user);
}
