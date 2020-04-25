package cz.muni.fi.facades;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dto.AnimalListDTO;
import cz.muni.fi.dto.FoodWebDTO;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.entities.FoodChain;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class FoodWebFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private FoodChainService foodChainService;

    @Mock
    private AnimalService animalService;

    @Spy
    @Autowired
    private BeanMappingService beanMappingService;


    @InjectMocks
    private FoodWebFacade foodWebFacade = new FoodWebFacadeImpl();

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
        foodChainAnimalList.add(fox);
        standardFoodChain2.setAnimals(foodChainAnimalList);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(foodChainService);
        Mockito.reset(animalService);
    }

    @Test
    public void getFoodWebFromAllFoodChainsTest() {
        Mockito.when(animalService.getAllAnimals()).thenReturn(new ArrayList<Animal>(){ {add(vole); add(fox); add(frog);} });

        Mockito.when(foodChainService.getAllFoodChains()).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(vole)).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(fox)).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(frog)).thenReturn(Collections.singletonList(standardFoodChain2));

        FoodWebDTO foodWebFromAllFoodChains = foodWebFacade.getFoodWebFromAllFoodChains();

        AnimalListDTO voleDTO = beanMappingService.mapTo(vole, AnimalListDTO.class);
        AnimalListDTO frogDTO = beanMappingService.mapTo(frog, AnimalListDTO.class);
        AnimalListDTO foxDTO = beanMappingService.mapTo(fox, AnimalListDTO.class);

        assertThat(foodWebFromAllFoodChains.getFoodWeb().keySet())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(voleDTO, frogDTO, foxDTO);

        assertThat(foodWebFromAllFoodChains.getFoodWeb().get(voleDTO))
                .isEmpty();

        assertThat(foodWebFromAllFoodChains.getFoodWeb().get(frogDTO))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(foxDTO);

        assertThat(foodWebFromAllFoodChains.getFoodWeb().get(foxDTO))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(voleDTO, frogDTO);
    }
}
