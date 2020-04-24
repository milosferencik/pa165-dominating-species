package cz.muni.fi.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.AnimalService;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.interfaces.AnimalDao;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import static org.mockito.Matchers.any;

/**
 * @author Katarina Matusova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AnimalServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private AnimalDao animalDao;

    @Autowired
    @InjectMocks
    private AnimalService animalService;

    private Animal wolf;
    private Animal salmon;
    private Animal deer;

    private Environment water;
    private Environment forest;
    private Environment sun;

    private List<Animal> forestAnimals;

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(animalDao);
    }

    @BeforeMethod
    public void setup() throws ServiceException {
        water = new Environment();
        water.setName("Water");
        water.setDescription("Ocean water");

        forest = new Environment();
        forest.setName("Forest");
        forest.setDescription("Big forest with deciduous tree");

        sun = new Environment();
        sun.setName("Sun");
        sun.setDescription("Nobody lives on sun");

        wolf = new Animal();
        wolf.setName("Wolf");
        wolf.setSpecies("Tundra wolf");
        wolf.setId(1L);
        wolf.setEnvironment(forest);

        salmon = new Animal();
        salmon.setName("Salmon");
        salmon.setSpecies("Sea fish");
        salmon.setId(2L);
        salmon.setEnvironment(water);

        deer = new Animal();
        deer.setName("Deer");
        deer.setSpecies("Red deer");
        deer.setId(3L);
        deer.setEnvironment(forest);

        forestAnimals = new ArrayList<>();
        forestAnimals.add(deer);
        forestAnimals.add(wolf);

    }

    @Test
    public void createAnimalTest() {
        animalService.createAnimal(wolf);
        Mockito.verify(animalDao).createAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createNullAnimalTest() {
        Mockito.doThrow(DataAccessException.class).when(animalDao).createAnimal(null);
        animalService.createAnimal(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createAnimalWithNullEnvironmentTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getEnvironment() == null ) {
                throw new IllegalArgumentException("Animal cannot have null environment.");
            }
            return animal;
        }).when(animalDao).createAnimal(any(Animal.class));

        wolf.setEnvironment(null);
        animalService.createAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createAnimalWithNullNameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getName() == null ) {
                throw new IllegalArgumentException("Animal cannot have null name.");
            }
            return animal;
        }).when(animalDao).createAnimal(any(Animal.class));

        wolf.setName(null);
        animalService.createAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createAnimalWithNullSpeciesTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getSpecies() == null ) {
                throw new IllegalArgumentException("Animal cannot have null species.");
            }
            return animal;
        }).when(animalDao).createAnimal(any(Animal.class));

        wolf.setSpecies(null);
        animalService.createAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createAnimalWithEmptySpeciesTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getSpecies().isEmpty() ) {
                throw new IllegalArgumentException("Animal cannot have empty species.");
            }
            return animal;
        }).when(animalDao).createAnimal(any(Animal.class));

        wolf.setSpecies("");
        animalService.createAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createAnimalWithEmptyNameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getName().isEmpty() ) {
                throw new IllegalArgumentException("Animal cannot have empty name.");
            }
            return animal;
        }).when(animalDao).createAnimal(any(Animal.class));

        wolf.setName("");
        animalService.createAnimal(wolf);
    }

    @Test
    public void findAnimalByIdTest() {
        Mockito.when(animalDao.getAnimal(wolf.getId())).thenReturn(wolf);

        Animal foundAnimal = animalService.getAnimal(wolf.getId());
        Mockito.verify(animalDao).getAnimal(wolf.getId());
        assertThat(foundAnimal).isEqualToComparingFieldByFieldRecursively(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void findAnimalByNonExistingIdTest() {
        Mockito.doThrow(DataAccessException.class).when(animalDao).getAnimal(4L);
        animalService.getAnimal(4L);
    }

    @Test
    public void findAnimalsByEnvironmentTest() {
        Mockito.when(animalDao.getAllAnimalsInEnvironment(forest)).thenReturn(forestAnimals);

        List<Animal> foundAnimals = animalService.getAnimalsByEnvironment(wolf.getEnvironment());
        Mockito.verify(animalDao).getAllAnimalsInEnvironment(forest);
        for (Animal animal : foundAnimals) {
            assertThat(animal.getEnvironment()).isEqualTo(forest);
        }
        assertThat(foundAnimals).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(wolf, deer);
    }

    @Test
    public void findAnimalsByEnvironmentNonExistingTest() {
        Mockito.when(animalDao.getAllAnimalsInEnvironment(sun)).thenReturn(Collections.emptyList());

        List<Animal> foundAnimals = animalService.getAnimalsByEnvironment(sun);
        Mockito.verify(animalDao).getAllAnimalsInEnvironment(sun);
        assertThat(foundAnimals).size().isEqualTo(0);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void findAnimalsByNullEnvironmentTest() {
        Mockito.doThrow(DataAccessException.class).when(animalDao).getAllAnimalsInEnvironment(null);
        animalService.getAnimalsByEnvironment(null);
    }

    @Test
    public void findAllAnimalsWithSingleAnimalStoredTest() {
        Mockito.when(animalDao.getAllAnimals()).thenReturn(Collections.singletonList(wolf));

        List<Animal> foundAnimals = animalService.getAllAnimals();
        Mockito.verify(animalDao).getAllAnimals();
        assertThat(foundAnimals).containsExactlyInAnyOrder(wolf);
    }

    @Test
    public void findAllAnimalsWithMultipleAnimalsStoredTest() {
        Mockito.when(animalDao.getAllAnimals()).thenReturn(Arrays.asList(wolf,deer,salmon));

        List<Animal> foundAnimals = animalService.getAllAnimals();
        Mockito.verify(animalDao).getAllAnimals();
        assertThat(foundAnimals).containsExactlyInAnyOrder(deer,salmon,wolf);
    }

    @Test
    public void updateTest() {
        wolf.setSpecies("Steppe wolf");
        animalService.updateAnimal(wolf);
        Mockito.verify(animalDao).updateAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateAnimalWithNullNameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getName() == null ) {
                throw new IllegalArgumentException("Animal cannot have null name.");
            }
            return animal;
        }).when(animalDao).updateAnimal(any(Animal.class));

        wolf.setName(null);
        animalService.updateAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateAnimalWithNullEnvironmentTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getEnvironment() == null ) {
                throw new IllegalArgumentException("Animal cannot have null environment.");
            }
            return animal;
        }).when(animalDao).updateAnimal(any(Animal.class));

        wolf.setEnvironment(null);
        animalService.updateAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateAnimalWithNullSpeciesTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getSpecies() == null ) {
                throw new IllegalArgumentException("Animal cannot have null species.");
            }
            return animal;
        }).when(animalDao).updateAnimal(any(Animal.class));

        wolf.setSpecies(null);
        animalService.updateAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateAnimalWithEmptySpeciesTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getSpecies().isEmpty() ) {
                throw new IllegalArgumentException("Animal cannot have empty species.");
            }
            return animal;
        }).when(animalDao).updateAnimal(any(Animal.class));

        wolf.setSpecies("");
        animalService.updateAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateAnimalWithEmptyNameTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Animal animal = invocationOnMock.getArgumentAt(0, Animal.class);
            if (animal == null || animal.getName().isEmpty() ) {
                throw new IllegalArgumentException("Animal cannot have empty name.");
            }
            return animal;
        }).when(animalDao).updateAnimal(any(Animal.class));

        wolf.setName("");
        animalService.updateAnimal(wolf);
    }

    @Test
    public void removeAnimalTest() {
        animalService.deleteAnimal(wolf);
        Mockito.verify(animalDao).deleteAnimal(wolf);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void removeNullAnimalTest() {
        Mockito.doThrow(DataAccessException.class).when(animalDao).deleteAnimal(null);
        animalService.deleteAnimal(null);
    }
}
