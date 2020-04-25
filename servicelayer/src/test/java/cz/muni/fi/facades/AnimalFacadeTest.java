package cz.muni.fi.facades;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dto.AnimalCreateDTO;
import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.dto.AnimalListDTO;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.EnvironmentService;
import dao.entities.Animal;
import dao.entities.Environment;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

/**
 * @author Milos Ferencik 25/4/2020
 */
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class AnimalFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EnvironmentService environmentService;

    @Mock
    private AnimalService animalService;

    @Spy
    @Autowired
    private BeanMappingService beanMappingService;

    @InjectMocks
    private AnimalFacade animalFacade = new AnimalFacadeImpl();

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Animal vole;
    private Animal fox;

    private Environment field;

    List<Animal> animalList;

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

        animalList = new ArrayList<>();
        animalList.add(vole);
        animalList.add(fox);

    }

    @AfterMethod
    public void reset() {
        Mockito.reset(environmentService);
        Mockito.reset(animalService);
    }

    @Test
    public void createAnimalTest() {
        Mockito.when(environmentService.getEnvironment(field.getId())).thenReturn(field);

        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            animal.setId(2L);
            return animal;
        }).when(animalService).createAnimal(any(Animal.class));

        AnimalCreateDTO foxCreateDTO = new AnimalCreateDTO();
        foxCreateDTO.setName(fox.getName());
        foxCreateDTO.setSpecies(fox.getSpecies());
        foxCreateDTO.setEnvironmentId(field.getId());
        Long id = animalFacade.createAnimal(foxCreateDTO);
        assertThat(id).isEqualTo(2L);
    }

    @Test
    public void updateAnimalTest() {
        AnimalDTO foxDTO = beanMappingService.mapTo(fox, AnimalDTO.class);
        ArgumentCaptor<Animal> argument = ArgumentCaptor.forClass(Animal.class);
        animalFacade.updateAnimal(foxDTO);
        Mockito.verify(animalService).updateAnimal(argument.capture());
        assertThat(argument.getValue()).isEqualToComparingFieldByField(fox);
    }

    @Test
    public void deleteAnimalTest() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        animalFacade.deleteAnimal(fox.getId());
        Mockito.verify(animalService).deleteAnimal(argument.capture());
        assertThat(argument.getValue()).isEqualTo(fox.getId());

    }

    @Test
    public void getAnimalByIdTest() {
        Mockito.when(animalService.getAnimal(fox.getId())).thenReturn(fox);
        AnimalDTO animal = animalFacade.getAnimalById(fox.getId());
        assertThat(animal).isEqualToComparingFieldByField(beanMappingService.mapTo(fox, AnimalDTO.class));
    }

    @Test
    public void getAllAnimalsTest() {
        Mockito.when(animalService.getAllAnimals()).thenReturn(animalList);
        List<AnimalListDTO> animals = animalFacade.getAllAnimals();
        assertThat(animals).isEqualTo(beanMappingService.mapTo(animalList, AnimalListDTO.class));
    }

    @Test
    public void getAnimalsByEnvironmentTest() {
        Mockito.when(environmentService.getEnvironment(field.getId())).thenReturn(field);
        Mockito.when(animalService.getAnimalsByEnvironment(field)).thenReturn(animalList);
        List<AnimalListDTO> animals = animalFacade.getAnimalsByEnvironment(field.getId());
        assertThat(animals).isEqualTo(beanMappingService.mapTo(animalList, AnimalListDTO.class));
    }
}
