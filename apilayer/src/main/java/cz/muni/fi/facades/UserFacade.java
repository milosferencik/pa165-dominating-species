package cz.muni.fi.facades;

import cz.muni.fi.dto.UserCreateDTO;
import cz.muni.fi.dto.UserDTO;

/**
 * @autor on 25/04/2020.
 */
public interface UserFacade {
    /**
     * Create new user
     * @param userCreateDTO
     * @return
     */
    Long createUser(UserCreateDTO userCreateDTO);

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
     * @return
     */
    UserDTO getUserById(Long id);
}
