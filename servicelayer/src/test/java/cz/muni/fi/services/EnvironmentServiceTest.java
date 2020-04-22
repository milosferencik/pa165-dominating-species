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


    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setup() throws ServiceException {
    }

    @AfterMethod
    public void reset(){
        Mockito.reset(environmentDao);
    }




}
