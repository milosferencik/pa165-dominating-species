package cz.muni.fi.pa165.dominatingspecies.api.facades;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AuthenticateUserDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.UserCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.UserDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.UserUpdateDTO;

import java.util.List;

/**
 * @Author Kostka on 25/04/2020.
 */
public interface UserFacade {
    /**
     * Create new user
     * @param userCreateDTO
     * @return
     */
    Long createUser(UserCreateDTO userCreateDTO, String password);

    /**
     * Update user
     * @param userDTO
     */
    void updateUser(UserUpdateDTO userDTO);

    /**
     * Delete user
     * @param id id of user to be deleted
     */
    void deleteUser(Long id);

    /**
     * Find user with the id
     * @param id id of user to be found
     * @return user found
     */
    UserDTO getUserById(Long id);

    /**
     * Find user by given email
     * @param email email by which to find the user
     * @return user foun
     */
    UserDTO getUserByEmail(String email);

    /**
     * Check whether user of given id has admin rights
     * @param id id of user to check whether he is an admin
     * @return true if user is admin, false otherwise
     */
    boolean isUserAdmin(Long id);

    /**
     * Return information about all users
     *
     * @return list of all users
     */
    List<UserDTO> getAllUsers();

    /**
     * Try to authenticate a user
     * @param user user's authentication DTO
     * @return True, if the authentication was successful, false otherwise
     */
    boolean authenticate(AuthenticateUserDTO user);

    /**
     * Change user's password
     * @param userDTO DTO of user
     * @param password current password of user
     * @param newPassword new password
     * @return true if password was changed successfully, false otherwise
     */
     boolean changePassword(UserDTO userDTO, String password, String newPassword);

}