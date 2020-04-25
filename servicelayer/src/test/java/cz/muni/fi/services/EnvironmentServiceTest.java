package cz.muni.fi.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.EnvironmentService;
import dao.entities.Environment;
import dao.interfaces.EnvironmentDao;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

/**
 * @author Kostka on 22/04/2020.
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class EnvironmentServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private EnvironmentDao environmentDao;

    @Autowired
    @InjectMocks
    private EnvironmentService environmentService;

    private Environment dam;
    private Environment forest;
    private Environment marsh;

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setup() throws ServiceException {
        marsh = new Environment();
        marsh.setName("Marsh");
        marsh.setDescription("45 % of water and 55 % of mug");

        dam = new Environment();
        dam.setName("Dam");
        dam.setDescription("95 % of fresh water and 5 % of mug");

        forest = new Environment();
        forest.setName("Forest");
        forest.setDescription("50 % of coniferous trees and 50 % broadleaved trees ");
    }

    @AfterMethod
    public void reset(){
        Mockito.reset(environmentDao);
    }

    @Test
    public void createEnvironmentTest() {
        environmentService.createEnvironment(dam);
        Mockito.verify(environmentDao).createEnvironment(dam);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createEnvironmentWithNullTest() {
        doThrow(DataAccessException.class).when(environmentDao).createEnvironment(null);

        environmentService.createEnvironment(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void testCreateEnvironmentWithNullName() throws Exception {
        addEnvironmentManipulationMethodsMock();
        dam.setName(null);
        environmentService.createEnvironment(dam);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void testCreateEnvironmentWithEmptyName() throws Exception {
        addEnvironmentManipulationMethodsMock();
        dam.setName("");
        environmentService.createEnvironment(dam);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void testCreateEnvironmentWithNullDescription() throws Exception {
        addEnvironmentManipulationMethodsMock();
        dam.setDescription(null);
        environmentService.createEnvironment(dam);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void testCreateEnvironmentWithEmptyDescription() throws Exception {
        addEnvironmentManipulationMethodsMock();
        dam.setDescription("");
        environmentService.createEnvironment(dam);
    }

    @Test
    public void updateTest() {
        environmentService.createEnvironment(dam);
        dam.setName("Forest");
        dam.setDescription("50 % of coniferous trees and 50 % broadleaved trees ");
        environmentService.updateEnvironment(dam);
        Mockito.verify(environmentDao).updateEnvironment(dam);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testUpdateNullEnvironment() throws Exception {
        doThrow(DataAccessException.class).when(environmentDao).updateEnvironment(null);
        environmentService.updateEnvironment(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void testUpdateEnvironmentWithNullName() throws Exception {
        addEnvironmentManipulationMethodsMock();
        dam.setName(null);
        environmentService.updateEnvironment(dam);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void testUpdateEnvironmentWithEmptyName() throws Exception {
        addEnvironmentManipulationMethodsMock();
        dam.setName("");
        environmentService.updateEnvironment(dam);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void testUpdateEnvironmentWithNullDescription() throws Exception {
        addEnvironmentManipulationMethodsMock();
        dam.setDescription(null);
        environmentService.updateEnvironment(dam);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void testUpdateEnvironmentWithEmptyDescription() throws Exception {
        addEnvironmentManipulationMethodsMock();
        dam.setDescription("");
        environmentService.updateEnvironment(dam);
    }

    @Test
    public void DeleteEnvironmentTest() {
        addEnvironmentManipulationMethodsMock();
        environmentService.createEnvironment(dam);
        environmentService.deleteEnvironment(dam.getId());
        assertThat(environmentService.getAllEnvironments()).isEmpty();
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteEnvironmentWithNull() throws Exception {
        doThrow(DataAccessException.class).when(environmentDao).deleteEnvironment(null);
        addEnvironmentManipulationMethodsMock();
        environmentService.deleteEnvironment(null);
    }

    @Test
    public void testGetAllEnvironment() {
        addEnvironmentManipulationMethodsMock();
        Mockito.when(environmentDao.getAllEnvironments()).thenReturn(Collections.singletonList(dam));

        List<Environment> found = environmentService.getAllEnvironments();
        Mockito.verify(environmentDao).getAllEnvironments();
        assertThat(found).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(dam);
    }
    @Test
    public void testGetAllEnvironmentOnEmptyDB() {
        addEnvironmentManipulationMethodsMock();
        assertThat(environmentService.getAllEnvironments()).isEmpty();
    }

    @Test
    public void testGetEnvironment() {
        addEnvironmentManipulationMethodsMock();
        environmentService.createEnvironment(dam);
        assertThat(environmentService.getEnvironment(dam.getId())).isEqualToComparingFieldByField(dam);
    }

    private void addEnvironmentManipulationMethodsMock() {
        addEnvironmentCreationMethodMock();
        addEnvironmentUpdateMethodMock();
        adGetEnvironmentByIdMethodMock();
    }

    private void addEnvironmentCreationMethodMock() {
        doAnswer(invocationOnMock -> {
            Environment e = invocationOnMock.getArgumentAt(0, Environment.class);
            if (e == null || e.getName() == null ||  e.getName().equals("") || e.getDescription() == null
                    || e.getDescription().equals("")) {
                throw new IllegalArgumentException("Environment cannot be null");
            }
            return e;
        }).when(environmentDao).createEnvironment(any(Environment.class));
    }

    private void addEnvironmentUpdateMethodMock() {
        doAnswer(invocationOnMock -> {
            Environment e = invocationOnMock.getArgumentAt(0, Environment.class);
            if (e == null || e.getName() == null ||  e.getName().equals("") || e.getDescription() == null
                    || e.getDescription().equals("")) {
                throw new IllegalArgumentException("Environment cannot be null");
            }
            return e;
        }).when(environmentDao).updateEnvironment(any(Environment.class));
    }

    private void adGetEnvironmentByIdMethodMock() {
        Mockito.when(environmentDao.getEnvironment(dam.getId())).thenReturn(dam);
    }






}
