package cz.muni.fi.services.implementations;

import cz.muni.fi.services.interfaces.UserService;
import dao.entities.User;
import dao.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void createUser(User user) {
        userDao.createUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
         return userDao.getUserByEmail(email);
    }

    @Override
    public boolean isAdmin(User user) {
        return getUser(user.getId()).isAdmin();
    }

}
