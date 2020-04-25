package cz.muni.fi.facades;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.entities.FoodChain;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class FoodWebFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private FoodChainService foodChainService;

    @Mock
    private AnimalService animalService;

    @Autowired
    @InjectMocks
    private FoodWebFacade foodWebFacade;

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private FoodChain standardFoodChain;
    private FoodChain standardFoodChain2;

    private Animal vole;
    private Animal fox;
    private Animal frog;

    private Environment field;

    List<Animal> foodChainAnimalList;

    @BeforeMethod
    public void setup() throws ServiceException {
        field = new Environment();
        field.setId(1L);
        field.setName("Field");
        field.setDescription("Regular field");

        vole = new Animal();
        vole.setId(1L);
        vole.setName("Vole");
        vole.setSpecies("Bank Vole");
        vole.setEnvironment(field);

        fox = new Animal();
        fox.setId(2L);
        fox.setName("Fox");
        fox.setSpecies("Red Fox");
        fox.setEnvironment(field);

        frog = new Animal();
        frog.setId(3L);
        frog.setName("Frog");
        frog.setSpecies("Big Frog");
        frog.setEnvironment(field);

        standardFoodChain = new FoodChain();
        standardFoodChain2 = new FoodChain();

        standardFoodChain.setId(1L);
        standardFoodChain.setId(2L);

        foodChainAnimalList = new ArrayList<>();

        foodChainAnimalList.add(vole);
        foodChainAnimalList.add(fox);

        standardFoodChain.setAnimals(foodChainAnimalList);

        foodChainAnimalList.add(frog);
        standardFoodChain2.setAnimals(foodChainAnimalList);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(foodChainService);
        Mockito.reset(animalService);
    }

    @Test
    public void getFoodWebFromAllFoodChainsTest() {
        Mockito.when(animalService.getAllAnimals())
                .thenReturn(new ArrayList<Animal>(){ {add(vole); add(fox); add(frog);} });
        Mockito.when(foodChainService.getAllFoodChains()).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(vole)).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(fox)).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(frog)).thenReturn(Collections.singletonList(standardFoodChain2));



        assertThat(foodWebFacade.getFoodWebFromAllFoodChains()).isNotNull();
    }
}
