package dao.interfaces;

import dao.entities.User;

import java.util.List;

/**
 * Created by Kostka on 23/03/2020.
 */
public interface UserDao {
    public void createAnimal(User user);
    public List<User> getAllUsers();
    public User getUser(Long id);
    public void updateUser(User user);
    public void deleteUser(User user);
}
