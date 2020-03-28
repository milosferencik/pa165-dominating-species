package dao;

import dao.config.MainConfiguration;
import dao.entities.Environment;
import dao.interfaces.EnvironmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = MainConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EnvironmentImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EnvironmentDao environmentDao;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private Environment dam;
    private Environment forest;
    private Environment marsh;

    @BeforeMethod
    public void setUp() throws Exception {
        dam = new Environment();
        dam.setName("Dam");
        dam.setDescription("95 % of fresh water and 5 % of mug");

        forest = new Environment();
        forest.setName("Forest");
        forest.setDescription("50 % of coniferous trees and 50 % broadleaved trees ");

        marsh = new Environment();
        marsh.setName("Marsh");
        marsh.setDescription("45 % of water and 55 % of mug");
    }

    @Test
    public void testCreateEnvironment() {
        environmentDao.createEnvironment(dam);
        assertThat(environmentDao.getAllEnvironments()).hasSize(1);
        environmentDao.createEnvironment(forest);
        assertThat(environmentDao.getAllEnvironments()).hasSize(2);
        assertThat(environmentDao.getEnvironment(dam.getId())).isEqualTo(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEnvironmentWithId() throws Exception {
        dam.setId(1L);
        environmentDao.createEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEnvironmentWithNullName() throws Exception {
        dam.setName(null);
        environmentDao.createEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEnvironmentWithEmptyName() throws Exception {
        dam.setName("");
        environmentDao.createEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEnvironmentWithNullDescription() throws Exception {
        dam.setDescription(null);
        environmentDao.createEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEnvironmentWithEmptyDescription() throws Exception {
        dam.setDescription("");
        environmentDao.createEnvironment(dam);
    }

    @Test
    public void testUpdateEnvironment() {
        environmentDao.createEnvironment(dam);
        dam.setName("Forest");
        dam.setDescription("50 % of coniferous trees and 50 % broadleaved trees ");
        environmentDao.updateEnvironment(dam);
        assertThat(environmentDao.getEnvironment(dam.getId())).isEqualToComparingFieldByField(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateEnvironmentWithNullName() throws Exception {
        environmentDao.createEnvironment(dam);
        dam.setName(null);
        environmentDao.updateEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateEnvironmentWithEmptyName() throws Exception {
        environmentDao.createEnvironment(dam);
        dam.setName("");
        environmentDao.updateEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateEnvironmentWithNullDescription() throws Exception {
        environmentDao.createEnvironment(dam);
        dam.setDescription(null);
        environmentDao.updateEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateEnvironmentWithEmptyDescription() throws Exception {
        environmentDao.createEnvironment(dam);
        dam.setDescription("");
        environmentDao.updateEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateEnvironmentWithNullId() throws Exception {
        environmentDao.createEnvironment(dam);
        dam.setId(null);
        environmentDao.updateEnvironment(dam);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateEnvironmentWithId() throws Exception {
        environmentDao.createEnvironment(dam);
        dam.setId(4L);
        environmentDao.updateEnvironment(dam);
    }

    @Test
    public void testDeleteEnvironment() {
        environmentDao.createEnvironment(dam);
        assertThat(environmentDao.getAllEnvironments()).isEqualTo(Collections.singletonList(dam));
        environmentDao.deleteEnvironment(dam);
        assertThat(environmentDao.getAllEnvironments()).isEmpty();
    }

    @Test
    public void testGetAllEnvironment() {
        environmentDao.createEnvironment(dam);
        environmentDao.createEnvironment(forest);
        environmentDao.createEnvironment(marsh);
        assertThat(environmentDao.getAllEnvironments()).hasSize(3);
    }

    @Test
    public void testGetAllEnvironmentOnEmptyDB() {
        assertThat(environmentDao.getAllEnvironments()).isEmpty();
    }

    @Test
    public void testGetEnvironment() {
        environmentDao.createEnvironment(dam);
        environmentDao.createEnvironment(forest);
        environmentDao.createEnvironment(marsh);
        assertThat(environmentDao.getEnvironment(dam.getId())).isEqualToComparingFieldByField(dam);
        assertThat(environmentDao.getEnvironment(forest.getId())).isEqualToComparingFieldByField(forest);
        assertThat(environmentDao.getEnvironment(marsh.getId())).isEqualToComparingFieldByField(marsh);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetEnvironmentWithNullId() throws Exception{
        environmentDao.createEnvironment(dam);
        environmentDao.getEnvironment(null);
    }
}
