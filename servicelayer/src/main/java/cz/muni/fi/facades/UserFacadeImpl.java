package cz.muni.fi.facades;

import cz.muni.fi.dto.AuthenticateUserDTO;
import cz.muni.fi.dto.UserCreateDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.dto.UserUpdateDTO;
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
        User storedUser = userService.getUser(userDTO.getId());
        storedUser.setSurname(userDTO.getSurname());
        storedUser.setEmail(userDTO.getEmail());
        storedUser.setName(userDTO.getName());
        userService.updateUser(storedUser);
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
        User user = userService.getUserByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
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

}
