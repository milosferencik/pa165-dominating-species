package dao;

import dao.config.MainConfiguration;
import dao.entities.User;
import dao.interfaces.UserDao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Collections;
import static org.assertj.core.api.Assertions.*;


/**
 *  Created by Matusova on 27/03/2020.
 */
@ContextConfiguration(classes = MainConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserImplTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private UserDao userDao;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private User u1;
    private User u2;

    @BeforeMethod
    public void setUp() throws Exception {
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
        u2.setPasswordHash("955db0b81ef1989b4a4dfeae8061a9a6");
        u2.setAdmin(true);
    }


    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNullUser() throws Exception {
        userDao.createUser(null);
    }

    @Test
    public void testCreateUser() {
        userDao.createUser(u1);
        assertThat(userDao.getAllUsers()).hasSize(1);
        userDao.createUser(u2);
        assertThat(userDao.getAllUsers()).hasSize(2);
        assertThat(userDao.getUser(u2.getId())).isEqualTo(u2);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithNullName() throws Exception {
        u1.setName(null);
        userDao.createUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithEmptyName() throws Exception {
        u1.setName("");
        userDao.createUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithNullSurname() throws Exception {
        u1.setSurname(null);
        userDao.createUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithEmptySurname() throws Exception {
        u1.setSurname("");
        userDao.createUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithNullEmail() throws Exception {
        u1.setEmail(null);
        userDao.createUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithEmptyEmail() throws Exception {
        u1.setEmail("");
        userDao.createUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithNullPasswordHash() throws Exception {
        u1.setPasswordHash(null);
        userDao.createUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithEmptyPasswordHash() throws Exception {
        u1.setPasswordHash("");
        userDao.createUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateUserWithId() throws Exception {
        u1.setId(1L);
        userDao.createUser(u1);
    }

    @Test
    public void testUpdateUser() {
        userDao.createUser(u1);
        u1.setName("John");
        u1.setEmail("johndoe@fi.cz");
        userDao.updateUser(u1);
        assertThat(userDao.getUser(u1.getId())).isEqualToComparingFieldByField(u1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testUpdateNullUser() {
        userDao.updateUser(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithNullName() throws Exception {
        userDao.createUser(u1);
        u1.setName(null);
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithEmptyName() throws Exception {
        userDao.createUser(u1);
        u1.setName("");
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithNullSurname() throws Exception {
        userDao.createUser(u1);
        u1.setSurname(null);
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithEmptySurname() throws Exception {
        userDao.createUser(u1);
        u1.setSurname("");
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithNullEmail() throws Exception {
        userDao.createUser(u1);
        u1.setEmail(null);
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithEmptyEmail() throws Exception {
        userDao.createUser(u1);
        u1.setEmail("");
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithNullPasswordHash() throws Exception {
        userDao.createUser(u1);
        u1.setPasswordHash(null);
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithEmptyPasswordHash() throws Exception {
        userDao.createUser(u1);
        u1.setPasswordHash("");
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithNullId() throws Exception {
        userDao.createUser(u1);
        u1.setId(null);
        userDao.updateUser(u1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserWithId() throws Exception {
        userDao.createUser(u1);
        u1.setId(2L);
        userDao.updateUser(u1);
    }

    @Test
    public void testDeleteUser() {
        userDao.createUser(u1);
        assertThat(userDao.getAllUsers()).isEqualTo(Collections.singletonList(u1));
        userDao.deleteUser(u1);
        assertThat(userDao.getAllUsers()).isEmpty();
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNullUser() {
        userDao.deleteUser(null);
    }

    @Test
    public void testGetAllUsers() {
        userDao.createUser(u1);
        userDao.createUser(u2);
        assertThat(userDao.getAllUsers()).hasSize(2);
    }

    @Test
    public void testGetAllUsersOnEmptyDB() {
        assertThat(userDao.getAllUsers()).isEmpty();
    }

    @Test
    public void testGetUser() {
        userDao.createUser(u1);
        userDao.createUser(u2);
        assertThat(userDao.getUser(u1.getId())).isEqualToComparingFieldByField(u1);
        assertThat(userDao.getUser(u2.getId())).isEqualToComparingFieldByField(u2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetUserWithNullId() throws Exception{
        userDao.createUser(u1);
        userDao.getUser(null);
    }

    @Test
    public void testGetUserWithNonExistentId() {
        assertThat(userDao.getUser(5L)).isNull();
    }

}
