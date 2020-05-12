package cz.muni.fi.facades;

import cz.muni.fi.dto.AuthenticateUserDTO;
import cz.muni.fi.dto.UserCreateDTO;
import cz.muni.fi.dto.UserDTO;
import org.graalvm.compiler.lir.LIRInstruction;

import java.util.List;

/**
 * @autor on 25/04/2020.
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
    void updateUser(UserDTO userDTO);

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
     * Checks whether user of given id has admin rights
     * @param id id of user to check whether he is an admin
     * @return true if user is admin, false otherwise
     */
    boolean isUserAdmin(Long id);

    /**
     * Returns information about all users
     *
     * @return list of all users
     */
    List<UserDTO> getAllUsers();

    boolean authenticate(AuthenticateUserDTO user);

}
