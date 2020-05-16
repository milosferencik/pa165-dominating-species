package cz.muni.fi.facades;

import cz.muni.fi.dto.AuthenticateUserDTO;
import cz.muni.fi.dto.UserCreateDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.dto.UserUpdateDTO;
import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.UserService;
import dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author Kostka on 25/04/2020.
 */

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private UserService userService;

    @Override
    public Long createUser(UserCreateDTO userCreateDTO, String password) {
        User user = beanMappingService.mapTo(userCreateDTO, User.class);
        userService.createUser(user, password);
        return user.getId();
    }

    @Override
    public void updateUser(UserUpdateDTO userDTO) {
        User user = userService.getUser(userDTO.getId());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setAdmin(userDTO.isAdmin());
        userService.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userService.getUser(id);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        try {
            User user = userService.getUserByEmail(email);
            return beanMappingService.mapTo(user, UserDTO.class);
        } catch (ServiceDataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean isUserAdmin(Long id) {
        return userService.isAdmin(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return beanMappingService.mapTo(users, UserDTO.class);
    }

    @Override
    public boolean authenticate(AuthenticateUserDTO user) {
        return userService.authenticate(userService.getUserByEmail(user.getEmail()), user.getPassword());
    }

    @Override
    public boolean changePassword(UserDTO userDTO, String password, String newPassword) {
        User user = beanMappingService.mapTo(userDTO, User.class);
        return userService.changePassword(user, password, newPassword);
    }

    @Override
    public void makeAdmin(Long id) {
        User user = userService.getUser(id);
        userService.grantPermission(user, true);
    }

    @Override
    public void revokeAdmin(Long id) {
        User user = userService.getUser(id);
        userService.grantPermission(user, false);
    }
}