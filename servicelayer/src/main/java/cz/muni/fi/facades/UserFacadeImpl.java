package cz.muni.fi.facades;

import cz.muni.fi.dto.UserCreateDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.UserService;
import dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public Long createUser(UserCreateDTO userCreateDTO) {
        User user = beanMappingService.mapTo(userCreateDTO, User.class);
        userService.createUser(user);
        return user.getId();
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = beanMappingService.mapTo(userDTO, User.class);
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
        User user = userService.getUserByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public boolean isUserAdmin(Long id) {
        return userService.isAdmin(id);
    }
}
