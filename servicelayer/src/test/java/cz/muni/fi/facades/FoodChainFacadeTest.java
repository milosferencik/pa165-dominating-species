package cz.muni.fi.facades;


import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.dto.AnimalInFoodChainDTO;
import cz.muni.fi.dto.FoodChainCreateDTO;
import cz.muni.fi.dto.FoodChainDTO;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.AnimalInFoodChain;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class FoodChainFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private FoodChainService foodChainService;

    @Mock
    private AnimalService animalService;

    @Spy
    @Autowired
    private BeanMappingService beanMappingService;

    @InjectMocks
    private FoodChainFacade foodChainFacade = new FoodChainFacadeImpl();

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
        standardFoodChain.setId(1L);
        emptyFoodChain = new FoodChain();
        emptyFoodChain.setId(2L);

        foodChainAnimalList = new ArrayList<>();

        foodChainAnimalList.add(vole);
        foodChainAnimalList.add(fox);

        standardFoodChain.setAnimals(foodChainAnimalList);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(foodChainService);
    }

    @Test
    public void createFoodChainTest() {
        FoodChainCreateDTO foodChainCreateDTO = beanMappingService.mapTo(standardFoodChain, FoodChainCreateDTO.class);
        Long id = foodChainFacade.createFoodChain(foodChainCreateDTO);
        verify(foodChainService).createFoodChain(standardFoodChain);
    }

    @Test
    public void updateFoodChainTest() {
        FoodChainDTO foodChainDTO = beanMappingService.mapTo(standardFoodChain, FoodChainDTO.class);
        foodChainAnimalList.add(frog);
        standardFoodChain.setAnimals(foodChainAnimalList);
        foodChainFacade.updateFoodChain(foodChainDTO);
        verify(foodChainService).updateFoodChain(beanMappingService.mapTo(foodChainDTO, FoodChain.class));
    }

    @Test
    public void delete() {
        /*foodChainFacade.deleteFoodChain(standardFoodChain.getId());
        verify(foodChainService).deleteFoodChain(standardFoodChain);*/

        ArgumentCaptor<FoodChain> argument = ArgumentCaptor.forClass(FoodChain.class);
        foodChainFacade.deleteFoodChain(standardFoodChain.getId());
        verify(foodChainService).deleteFoodChain(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(standardFoodChain.getId());

    }

    @Test
    public void getFoodChainByIdTest() {
        when(foodChainService.getFoodChain(standardFoodChain.getId())).thenReturn(standardFoodChain);
        FoodChainDTO foodChainDTO = foodChainFacade.getFoodChainById(standardFoodChain.getId());
        verify(foodChainService).getFoodChain(standardFoodChain.getId());
        assertThat(beanMappingService.mapTo(foodChainDTO, FoodChain.class)).isEqualTo(standardFoodChain);
    }

    @Test
    public void getAllFoodChainsTest() {
        foodChainAnimalList.add(frog);
        emptyFoodChain.setAnimals(foodChainAnimalList);
        when(foodChainService.getAllFoodChains()).thenReturn(Arrays.asList(standardFoodChain, emptyFoodChain));
        List<FoodChainDTO> foodChainDTOs = foodChainFacade.getAllFoodChains();
        verify(foodChainService).getAllFoodChains();
        List<FoodChain> foodChains = beanMappingService.mapTo(foodChainDTOs, FoodChain.class);
        assertThat(foodChains).containsOnly(standardFoodChain, emptyFoodChain);
    }

    @Test
    public void getFoodChainsWithAnimalTest() {
        //Mockito.when(animalService.getAnimal(vole.getId())).thenReturn(vole);
        Mockito.when(foodChainService.getFoodChainsWithAnimal(vole)).thenReturn(Collections.singletonList(standardFoodChain));
        List<FoodChainDTO> animals = foodChainFacade.getFoodChainsWithAnimal(vole.getId());
        assertThat(animals).containsExactly(beanMappingService.mapTo(standardFoodChain, FoodChainDTO.class));
    }

    @Test
    public void addAnimalToBeginningTest() {
        AnimalDTO animalDTO = beanMappingService.mapTo(frog, AnimalDTO.class);
        foodChainFacade.addAnimalToBeginning(animalDTO,standardFoodChain.getId());
        verify(foodChainService).addAnimalToBeginningOfFoodChain(beanMappingService.mapTo(animalDTO,Animal.class),standardFoodChain.getId());
    }

    @Test
    public void removeAnimalTest() {
        AnimalInFoodChain animalInFoodChain = standardFoodChain.getAnimalsInFoodChain().get(0);
        AnimalInFoodChainDTO animalDTO = beanMappingService.mapTo(animalInFoodChain, AnimalInFoodChainDTO.class);
        foodChainFacade.removeAnimal(animalDTO);
        verify(foodChainService).removeAnimal(beanMappingService.mapTo(animalDTO,AnimalInFoodChain.class));
    }

    @Test
    public void addAnimalToEndTest() {
        AnimalDTO animalDTO = beanMappingService.mapTo(frog, AnimalDTO.class);
        foodChainFacade.addAnimalToEnd(animalDTO,standardFoodChain.getId());
        verify(foodChainService).addAnimalToEndOfFoodChain(beanMappingService.mapTo(animalDTO,Animal.class),standardFoodChain.getId());
    }

}


