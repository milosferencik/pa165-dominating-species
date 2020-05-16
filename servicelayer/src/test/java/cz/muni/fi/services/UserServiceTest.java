package cz.muni.fi.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.UserService;
import dao.entities.User;
import dao.interfaces.UserDao;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

/**
 * @author Milos Ferencik on 23/04/2020.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    private User u1;
    private User u2;

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setup() throws ServiceException {
        u1 = new User();
        u1.setName("Jane");
        u1.setSurname("Doe");
        u1.setEmail("janedoe@muni.cz");
        u1.setPasswordHash("955db0b81ef1989b4a4dfeae8061a9a6");
        u1.setAdmin(false);

        u2 = new User();
        u2.setName("John");
        u2.setSurname("Doe");
        u2.setEmail("johndoe@muni.cz");
        u2.setPasswordHash("955db0b81ef1989b4a4dfeae8061a9b7");
        u2.setAdmin(true);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(userDao);
    }

    @Test
    public void createUserTest() {
        userService.createUser(u1);
        Mockito.verify(userDao).createUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithNullTest() {
        doThrow(IllegalArgumentException.class).when(userDao).createUser(null);

        userService.createUser(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithNullNameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getName() == null ) {
                throw new IllegalArgumentException("User cannot have null name.");
            }
            return user;
        }).when(userDao).createUser(any(User.class));

        u1.setName(null);
        userService.createUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithEmptyNameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getName().isEmpty()) {
                throw new IllegalArgumentException("User cannot have empty name.");
            }
            return user;
        }).when(userDao).createUser(any(User.class));

        u1.setName("");
        userService.createUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithNullSurnameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getSurname() == null ) {
                throw new IllegalArgumentException("User cannot have null surname.");
            }
            return user;
        }).when(userDao).createUser(any(User.class));

        u1.setSurname(null);
        userService.createUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithEmptySurnameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getSurname().isEmpty()) {
                throw new IllegalArgumentException("User cannot have empty surname.");
            }
            return user;
        }).when(userDao).createUser(any(User.class));

        u1.setSurname("");
        userService.createUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithInvalidEmailTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || !Pattern.matches(".+@.+\\..+", user.getEmail())) {
                throw new IllegalArgumentException("User cannot have incorrect email.");
            }
            return user;
        }).when(userDao).createUser(any(User.class));

        u1.setEmail("email");
        userService.createUser(u1);

        u1.setEmail(null);
        userService.createUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithNullPasswordHashTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getPasswordHash() == null) {
                throw new IllegalArgumentException("User cannot have null password hash.");
            }
            return user;
        }).when(userDao).createUser(any(User.class));

        u1.setPasswordHash(null);
        userService.createUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithEmptyPasswordHashTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getPasswordHash().isEmpty()) {
                throw new IllegalArgumentException("User cannot have empty password hash.");
            }
            return user;
        }).when(userDao).createUser(any(User.class));

        u1.setPasswordHash("");
        userService.createUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createUserWithIdTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getId() != null) {
                throw new IllegalArgumentException("User cannot have id before it is created.");
            }
            return user;
        }).when(userDao).createUser(any(User.class));

        u1.setId(1L);
        userService.createUser(u1);
    }

    @Test
    public void getAllUserTest() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(u1);
        expectedUsers.add(u2);

        Mockito.when(userDao.getAllUsers()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.getAllUsers();
        assertThat(actualUsers).hasSize(2);
        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    public void getAllUsersOnEmptyDBTest() {
        Mockito.when(userDao.getAllUsers()).thenReturn(new ArrayList<>());
        assertThat(userService.getAllUsers()).isEmpty();
    }

    @Test
    public void getUserTest() {
        u1.setId(1L);
        u2.setId(2L);

        Mockito.when(userDao.getUser(1L)).thenReturn(u1);
        Mockito.when(userDao.getUser(2L)).thenReturn(u2);

        assertThat(userService.getUser(u1.getId())).isEqualTo(u1);
        assertThat(userService.getUser(u2.getId())).isEqualTo(u2);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void getUserWithNullIdTest() {
        doThrow(IllegalArgumentException.class).when(userDao).getUser(null);

        userService.getUser(null);
    }

    @Test
    public void getUserWithNonExistentIdTest() {
        Mockito.when(userDao.getUser(1L)).thenReturn(null);
        assertThat(userService.getUser(1L)).isNull();
    }


    @Test
    public void updateUserTest() {
        u1.setEmail("doejane@muni.cz");
        userService.updateUser(u1);
        Mockito.verify(userDao).updateUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateUserWithNullTest() {
        doThrow(IllegalArgumentException.class).when(userDao).updateUser(null);

        userService.updateUser(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateUserWithNullNameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getName() == null ) {
                throw new IllegalArgumentException("User cannot have null name.");
            }
            return user;
        }).when(userDao).updateUser(any(User.class));

        u1.setName(null);
        userService.updateUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateUserWithEmptyNameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getName().isEmpty()) {
                throw new IllegalArgumentException("User cannot have empty name.");
            }
            return user;
        }).when(userDao).updateUser(any(User.class));

        u1.setName("");
        userService.updateUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateUserWithNullSurnameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getSurname() == null ) {
                throw new IllegalArgumentException("User cannot have null surname.");
            }
            return user;
        }).when(userDao).updateUser(any(User.class));

        u1.setSurname(null);
        userService.updateUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateUserWithEmptySurnameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getSurname().isEmpty()) {
                throw new IllegalArgumentException("User cannot have empty surname.");
            }
            return user;
        }).when(userDao).updateUser(any(User.class));

        u1.setSurname("");
        userService.updateUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateUserWithInvalidEmailTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || !Pattern.matches(".+@.+\\..+", user.getEmail())) {
                throw new IllegalArgumentException("User cannot have incorrect email.");
            }
            return user;
        }).when(userDao).updateUser(any(User.class));

        u1.setEmail("email");
        userService.updateUser(u1);

        u1.setEmail(null);
        userService.updateUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateUserWithNullPasswordHashTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getPasswordHash() == null) {
                throw new IllegalArgumentException("User cannot have null password hash.");
            }
            return user;
        }).when(userDao).updateUser(any(User.class));

        u1.setPasswordHash(null);
        userService.updateUser(u1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateUserWithEmptyPasswordHashTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            if (user == null || user.getPasswordHash().isEmpty()) {
                throw new IllegalArgumentException("User cannot have empty password hash.");
            }
            return user;
        }).when(userDao).updateUser(any(User.class));

        u1.setPasswordHash("");
        userService.updateUser(u1);
    }

    @Test
    public void removeUserTest() {
        userService.deleteUser(u1.getId());
        Mockito.verify(userDao).deleteUser(u1.getId());
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void removeNullUserTest() {
        Mockito.doThrow(DataAccessException.class).when(userDao).deleteUser(null);
        userService.deleteUser(null);
    }


    @Test
    public void getUserByEmailTest() {
        Mockito.when(userDao.getUserByEmail(u1.getEmail())).thenReturn(u1);
        Mockito.when(userDao.getUserByEmail(u2.getEmail())).thenReturn(u2);

        assertThat(userService.getUserByEmail(u1.getEmail())).isEqualToComparingFieldByField(u1);
        assertThat(userService.getUserByEmail(u2.getEmail())).isEqualToComparingFieldByField(u2);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void getUserByNullEmailTest() {
        doThrow(IllegalArgumentException.class).when(userDao).getUserByEmail(null);
        userService.getUserByEmail(null);
    }

    @Test
    public void getUserWithNonExistentEmailTest() {
        Mockito.when(userDao.getUserByEmail("email@email.email")).thenReturn(null);
        assertThat(userService.getUserByEmail("email@email.email")).isNull();
    }

    @Test
    public void isAdminTest() {
        u1.setId(1L);
        u2.setId(2L);

        Mockito.when(userDao.getUser(u1.getId())).thenReturn(u1);
        Mockito.when(userDao.getUser(u2.getId())).thenReturn(u2);

        assertThat(userService.isAdmin(u1.getId())).isFalse();
        assertThat(userService.isAdmin(u2.getId())).isTrue();
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void isAdminTestOnNonExistingId() {
        Mockito.when(userDao.getUser(1L)).thenReturn(null);

        userService.isAdmin(1L);
    }
}
