package cz.muni.fi.pa165.dominatingspecies.service.facades;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalListDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodWebDTO;
import cz.muni.fi.pa165.dominatingspecies.api.facades.FoodWebFacade;
import cz.muni.fi.pa165.dominatingspecies.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.AnimalService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.BeanMappingService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.FoodChainService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Animal;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.FoodChain;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
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
/**
 * @author  Ondrej Slimak on 25/04/2020.
 */

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
        addBasicFoodChainMethodMocks();
        Mockito.when(animalService.getAllAnimals()).thenReturn(new ArrayList<Animal>(){ {add(vole); add(fox); add(frog);} });

        FoodWebDTO foodWebFromAllFoodChains = foodWebFacade.getFoodWebFromAllFoodChains();
        assertFullFoodWebReceived(foodWebFromAllFoodChains);
    }

    @Test
    public void getFoodWebByEnvironmentHavingAllAnimalsTest() {
        addBasicFoodChainMethodMocks();
        Mockito.when(animalService.getAnimalsByEnvironment(field)).thenReturn(new ArrayList<Animal>(){ {add(vole); add(fox); add(frog);} });

        FoodWebDTO foodWeb = foodWebFacade.getFoodWebByEnvironment(beanMappingService.mapTo(field, EnvironmentDTO.class));
        assertFullFoodWebReceived(foodWeb);
    }

    @Test
    public void getFoodWebByEnvironmentNotHavingAllAnimalsTest() {
        addBasicFoodChainMethodMocks();
        frog.setEnvironment(new Environment());

        Mockito.when(animalService.getAnimalsByEnvironment(field)).thenReturn(new ArrayList<Animal>(){ {add(vole); add(fox);} });

        FoodWebDTO foodWeb = foodWebFacade.getFoodWebByEnvironment(beanMappingService.mapTo(field, EnvironmentDTO.class));

        AnimalListDTO voleDTO = beanMappingService.mapTo(vole, AnimalListDTO.class);
        AnimalListDTO frogDTO = beanMappingService.mapTo(frog, AnimalListDTO.class);
        AnimalListDTO foxDTO = beanMappingService.mapTo(fox, AnimalListDTO.class);

        assertThat(foodWeb.getFoodWeb().keySet())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(voleDTO, foxDTO);

        assertThat(foodWeb.getFoodWeb().get(voleDTO))
                .isEmpty();

        assertThat(foodWeb.getFoodWeb().get(frogDTO))
                .isNull();

        assertThat(foodWeb.getFoodWeb().get(foxDTO))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(voleDTO, frogDTO);
    }

    @Test
    public void getFoodWebByAnimalTest() {
        addBasicFoodChainMethodMocks();
        FoodWebDTO foodWeb = foodWebFacade.getFoodWebByAnimal(beanMappingService.mapTo(fox, AnimalDTO.class));

        AnimalListDTO voleDTO = beanMappingService.mapTo(vole, AnimalListDTO.class);
        AnimalListDTO frogDTO = beanMappingService.mapTo(frog, AnimalListDTO.class);
        AnimalListDTO foxDTO = beanMappingService.mapTo(fox, AnimalListDTO.class);

        assertThat(foodWeb.getFoodWeb().keySet())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(foxDTO);

        assertThat(foodWeb.getFoodWeb().get(voleDTO))
                .isNull();

        assertThat(foodWeb.getFoodWeb().get(frogDTO))
                .isNull();

        assertThat(foodWeb.getFoodWeb().get(foxDTO))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(voleDTO, frogDTO);
    }

    private void addBasicFoodChainMethodMocks() {
        Mockito.when(foodChainService.getAllFoodChains()).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(vole)).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(fox)).thenReturn(Arrays.asList(standardFoodChain, standardFoodChain2));
        Mockito.when(foodChainService.getFoodChainsWithAnimal(frog)).thenReturn(Collections.singletonList(standardFoodChain2));
    }

    private void assertFullFoodWebReceived(FoodWebDTO foodWeb) {
        AnimalListDTO voleDTO = beanMappingService.mapTo(vole, AnimalListDTO.class);
        AnimalListDTO frogDTO = beanMappingService.mapTo(frog, AnimalListDTO.class);
        AnimalListDTO foxDTO = beanMappingService.mapTo(fox, AnimalListDTO.class);

        assertThat(foodWeb.getFoodWeb().keySet())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(voleDTO, frogDTO, foxDTO);

        assertThat(foodWeb.getFoodWeb().get(voleDTO))
                .isEmpty();

        assertThat(foodWeb.getFoodWeb().get(frogDTO))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(foxDTO);

        assertThat(foodWeb.getFoodWeb().get(foxDTO))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(voleDTO, frogDTO);
    }
}
