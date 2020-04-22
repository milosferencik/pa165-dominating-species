package cz.muni.fi.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.EnvironmentService;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.entities.FoodChain;
import dao.interfaces.EnvironmentDao;
import dao.interfaces.FoodChainDao;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

/**
 * Created by Kostka on 22/04/2020.
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

    @Test
    public void updateTest() {
        environmentDao.createEnvironment(dam);
        dam.setName("Forest");
        dam.setDescription("50 % of coniferous trees and 50 % broadleaved trees ");
        environmentDao.updateEnvironment(dam);
        Mockito.verify(environmentDao).updateEnvironment(dam);
    }






}
