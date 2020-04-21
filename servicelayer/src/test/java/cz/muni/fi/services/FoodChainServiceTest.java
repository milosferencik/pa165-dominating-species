package cz.muni.fi.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.entities.FoodChain;
import dao.interfaces.FoodChainDao;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.*;


@ContextConfiguration(classes = ServiceConfiguration.class)
public class FoodChainServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private FoodChainDao foodChainDao;

    @Autowired
    @InjectMocks
    private FoodChainService foodChainService;

    private FoodChain standardFoodChain;
    private FoodChain emptyFoodChain;

    private Animal vole;
    private Animal fox;
    private Animal frog;

    List<Animal> foodChainAnimalList;
    
    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);

    }

    @BeforeMethod
    public void setup() throws ServiceException {
        Environment field = new Environment();
        field.setName("Field");
        field.setDescription("Regular field");

        vole = new Animal();
        vole.setName("Vole");
        vole.setSpecies("Bank Vole");
        vole.setEnvironment(field);

        fox = new Animal();
        fox.setName("Fox");
        fox.setSpecies("Red Fox");
        fox.setEnvironment(field);

        frog = new Animal();
        fox.setName("Frog");
        fox.setSpecies("Big Frog");
        fox.setEnvironment(field);

        standardFoodChain = new FoodChain();
        emptyFoodChain = new FoodChain();

        foodChainAnimalList = new ArrayList<>();

        foodChainAnimalList.add(vole);
        foodChainAnimalList.add(fox);

        standardFoodChain.setAnimals(foodChainAnimalList);
    }

    @Test
    public void createFoodChainTest() {
        foodChainService.createFoodChain(standardFoodChain);
        Mockito.verify(foodChainDao).createFoodChain(standardFoodChain);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createEmptyFoodChainTest() {
        foodChainService.createFoodChain(emptyFoodChain);
    }

    @Test
    public void updateTest() {
        foodChainAnimalList.add(frog);
        standardFoodChain.setAnimals(foodChainAnimalList);

        foodChainService.updateFoodChain(standardFoodChain);
        Mockito.verify(foodChainDao).updateFoodChain(standardFoodChain);
    }


    @Test
    public void getFoodChainByIdTest() {
        Mockito.when(foodChainDao.getFoodChain(standardFoodChain.getId())).thenReturn(standardFoodChain);

        FoodChain found = foodChainService.getFoodChain(standardFoodChain.getId());
        assertThat(found).isEqualToComparingFieldByFieldRecursively(standardFoodChain);
    }

    @Test
    public void getAllFoodChainsWithSingleFoodChainTest() {
        Mockito.when(foodChainDao.getAllFoodChains()).thenReturn(Collections.singletonList(standardFoodChain));

        List<FoodChain> found = foodChainService.getAllFoodChains();
        assertThat(found).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(standardFoodChain);
    }

    @Test
    public void getAllFoodChainsWithMultipleFoodChainsTest() {
        foodChainAnimalList.add(frog);
        emptyFoodChain.setAnimals(foodChainAnimalList);
        Mockito.when(foodChainDao.getAllFoodChains()).thenReturn(Arrays.asList(standardFoodChain, emptyFoodChain));

        List<FoodChain> found = foodChainService.getAllFoodChains();
        assertThat(found).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(standardFoodChain, emptyFoodChain);
    }



}


