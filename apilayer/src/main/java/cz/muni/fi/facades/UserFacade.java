package cz.muni.fi.facades;

import cz.muni.fi.dto.UserCreateDTO;
import cz.muni.fi.dto.UserDTO;

/**
 * Created by Kostka on 25/04/2020.
 */
public interface UserFacade {
    Long createUser(UserCreateDTO userCreateDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO getUserById(Long id);
}
