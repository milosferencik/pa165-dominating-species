package cz.muni.fi.services.implementations;

import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.UserService;
import dao.entities.User;
import dao.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void createUser(User user) throws DataAccessException {
        try {
            userDao.createUser(user);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not create user.", ex);
        }

    }

    @Override
    public List<User> getAllUsers() throws DataAccessException {
        try {
            return userDao.getAllUsers();
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not get all users.", ex);
        }
    }

    @Override
    public User getUser(Long id) throws DataAccessException {
        try {
            return userDao.getUser(id);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not get user.", ex);
        }
    }

    @Override
    public void updateUser(User user) throws DataAccessException {
        try {
            userDao.updateUser(user);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not update user.", ex);
        }
    }

    @Override
    public void deleteUser(User user) throws DataAccessException {
        try {
            userDao.deleteUser(user);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not delete user.", ex);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DataAccessException {
        try {
            return userDao.getUserByEmail(email);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not get user by email.", ex);
        }
    }

    @Override
    public boolean isAdmin(User user) throws DataAccessException {
        return getUser(user.getId()).isAdmin();
    }
}