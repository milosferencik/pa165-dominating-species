package cz.muni.fi.pa165.dominatingspecies.service.services;

import cz.muni.fi.pa165.dominatingspecies.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.dominatingspecies.service.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.FoodChainService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Animal;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.AnimalInFoodChain;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.FoodChain;
import cz.muni.fi.pa165.dominatingspecies.dao.interfaces.FoodChainDao;
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
 * @author Ondrej Slimak on 20/04/2020.
 */

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
        emptyFoodChain = new FoodChain();
        standardFoodChain.setId(1L);
        emptyFoodChain.setId(2L);

        foodChainAnimalList = new ArrayList<>();

        foodChainAnimalList.add(vole);
        foodChainAnimalList.add(fox);

        standardFoodChain.setAnimals(foodChainAnimalList);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(foodChainDao);
    }

    @Test
    public void createFoodChainTest() {
        foodChainService.createFoodChain(standardFoodChain);
        Mockito.verify(foodChainDao).createFoodChain(standardFoodChain);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createFoodChainWithNullTest() {
        doThrow(DataAccessException.class).when(foodChainDao).createFoodChain(null);

        foodChainService.createFoodChain(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createFoodChainWithNullListTest() {
        addFoodChainCreationMethodMock();
        standardFoodChain.setAnimalsInFoodChain(null);
        foodChainService.createFoodChain(standardFoodChain);
    }


    @Test
    public void createEmptyFoodChainTest() {
        addFoodChainCreationMethodMock();
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
        addGetStandardFoodChainByIdMethodMock();

        FoodChain found = foodChainService.getFoodChain(standardFoodChain.getId());
        Mockito.verify(foodChainDao).getFoodChain(standardFoodChain.getId());
        assertThat(found).isEqualToComparingFieldByFieldRecursively(standardFoodChain);
    }


    @Test
    public void getAllFoodChainsWithSingleFoodChainStoredTest() {
        Mockito.when(foodChainDao.getAllFoodChains()).thenReturn(Collections.singletonList(standardFoodChain));

        List<FoodChain> found = foodChainService.getAllFoodChains();
        Mockito.verify(foodChainDao).getAllFoodChains();
        assertThat(found).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(standardFoodChain);
    }

    @Test
    public void getAllFoodChainsWithMultipleFoodChainsStoredTest() {
        foodChainAnimalList.add(frog);
        emptyFoodChain.setAnimals(foodChainAnimalList);
        Mockito.when(foodChainDao.getAllFoodChains()).thenReturn(Arrays.asList(standardFoodChain, emptyFoodChain));

        List<FoodChain> found = foodChainService.getAllFoodChains();
        Mockito.verify(foodChainDao).getAllFoodChains();
        assertThat(found).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(standardFoodChain, emptyFoodChain);
    }


    @Test
    public void getFoodChainByAnimalWithOnlyOneCorrectFoodChainTest() {
        Mockito.when(foodChainDao.getFoodChainsWithAnimal(fox)).thenReturn(Collections.singletonList(standardFoodChain));

        List<FoodChain> found = foodChainService.getFoodChainsWithAnimal(fox);
        Mockito.verify(foodChainDao).getFoodChainsWithAnimal(fox);

        for (FoodChain foodChain : found) {
            assertThat(foodChain.getAnimals()).contains(fox);
        }
    }


    @Test
    public void getFoodChainByAnimalWithMultipleCorrectFoodChainsTest() {
        emptyFoodChain.setAnimals(foodChainAnimalList);
        Mockito.when(foodChainDao.getFoodChainsWithAnimal(fox)).thenReturn(Arrays.asList(standardFoodChain, emptyFoodChain));

        List<FoodChain> found = foodChainService.getFoodChainsWithAnimal(fox);
        Mockito.verify(foodChainDao).getFoodChainsWithAnimal(fox);

        for (FoodChain foodChain : found) {
            assertThat(foodChain.getAnimals()).contains(fox);
        }
    }

    @Test
    public void getFoodChainByAnimalWithNoCorrectFoodChainTest() {
        Mockito.when(foodChainDao.getFoodChainsWithAnimal(frog)).thenReturn(new ArrayList<>());

        List<FoodChain> found = foodChainService.getFoodChainsWithAnimal(frog);
        Mockito.verify(foodChainDao).getFoodChainsWithAnimal(frog);

        assertThat(found).isEmpty();
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void getFoodChainByNullAnimalTest() {
        doThrow(DataAccessException.class).when(foodChainDao).getFoodChainsWithAnimal(null);
        foodChainService.getFoodChainsWithAnimal(null);
    }

    @Test
    public void addAnimalToBeginningOfFoodChainCorrectlyTest() {
        addFoodChainManipulationMethodsMock();

        foodChainService.addAnimalToBeginningOfFoodChain(frog, standardFoodChain.getId());
        assertThat(standardFoodChain.getAnimals())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(frog, vole, fox);

        foodChainService.addAnimalToBeginningOfFoodChain(fox, standardFoodChain.getId());
        assertThat(standardFoodChain.getAnimals())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(fox, frog, vole, fox);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addNullAnimalToBeginningOfFoodChainTest() {
        foodChainService.addAnimalToBeginningOfFoodChain(null, standardFoodChain.getId());
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addAnimalToBeginningOfNullFoodChainIdTest() {
        foodChainService.addAnimalToBeginningOfFoodChain(fox, null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addAnimalToBeginningONonExistingFoodChainIdTest() {
        foodChainService.addAnimalToBeginningOfFoodChain(fox, 99L);
    }

    @Test
    public void addAnimalToEndOfFoodChainCorrectlyTest() {
        addFoodChainManipulationMethodsMock();

        foodChainService.addAnimalToEndOfFoodChain(frog, standardFoodChain.getId());
        assertThat(standardFoodChain.getAnimals())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(vole, fox, frog);

        foodChainService.addAnimalToEndOfFoodChain(fox, standardFoodChain.getId());
        assertThat(standardFoodChain.getAnimals())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(vole, fox, frog, fox);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addNullAnimalToEndOfFoodChainTest() {
        foodChainService.addAnimalToEndOfFoodChain(null, standardFoodChain.getId());
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addAnimalToEndOfNullFoodChainIdTest() {
        foodChainService.addAnimalToEndOfFoodChain(fox, null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addAnimalToEndOfNonExistingFoodChainIdTest() {
        foodChainService.addAnimalToEndOfFoodChain(fox, 99L);
    }

    @Test
    public void removeAnimalFromFoodChainCorrectlyTest() {
        addFoodChainExtendedManipulationMethodsMock();

        foodChainAnimalList.add(frog);
        foodChainAnimalList.add(fox);
        standardFoodChain.setAnimals(foodChainAnimalList);

        foodChainService.removeAnimal(standardFoodChain.getAnimalsInFoodChain().get(0));
        assertThat(standardFoodChain.getAnimals())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(fox, frog, fox);

        foodChainService.removeAnimal(standardFoodChain.getAnimalsInFoodChain().get(0));
        assertThat(standardFoodChain.getAnimals())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(frog, fox);
    }

    @Test
    public void removeFoodChain() {
        foodChainService.deleteFoodChain(standardFoodChain.getId());
        Mockito.verify(foodChainDao).deleteFoodChain(standardFoodChain.getId());
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void removeNullAnimalTest() {
        Mockito.doThrow(DataAccessException.class).when(foodChainDao).deleteFoodChain(null);
        foodChainService.deleteFoodChain(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void removeNullAnimalFromFoodChainTest() {
        foodChainService.removeAnimal(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void removeAnimalFromNullFoodChainIdTest() {
        AnimalInFoodChain invalidAnimalAssociation = standardFoodChain.getAnimalsInFoodChain().get(0);
        invalidAnimalAssociation.setAnimal(null);
        foodChainService.removeAnimal(invalidAnimalAssociation);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void removeAnimalFromNonExistingFoodChainIdTest() {
        AnimalInFoodChain invalidAnimalAssociation = standardFoodChain.getAnimalsInFoodChain().get(0);
        invalidAnimalAssociation.setFoodChain(null);
        foodChainService.removeAnimal(invalidAnimalAssociation);
    }

    private void addFoodChainExtendedManipulationMethodsMock() {
        addFoodChainManipulationMethodsMock();
        doAnswer(invocationOnMock -> {
            AnimalInFoodChain af = invocationOnMock.getArgumentAt(0, AnimalInFoodChain.class);
            if (af == null) {
                throw new IllegalArgumentException("FoodChain animals cannot be null.");
            }
            List<AnimalInFoodChain> animalsInFoodChain = af.getFoodChain().getAnimalsInFoodChain();
            int idx = animalsInFoodChain.indexOf(af);
            animalsInFoodChain.remove(af.getFoodChain().getAnimalsInFoodChain().get(idx));
            return null;
        }).when(foodChainDao).removeAnimalFromFoodChain(any(AnimalInFoodChain.class));
    }


    private void addFoodChainManipulationMethodsMock() {
        addFoodChainCreationMethodMock();
        addFoodChainUpdateMethodMock();
        addGetStandardFoodChainByIdMethodMock();
    }

    private void addFoodChainCreationMethodMock() {
        doAnswer(invocationOnMock -> {
            FoodChain fd = invocationOnMock.getArgumentAt(0, FoodChain.class);
            if (fd == null || fd.getAnimals() == null) {
                throw new IllegalArgumentException("FoodChain animals cannot be null.");
            }
            return fd;
        }).when(foodChainDao).createFoodChain(any(FoodChain.class));
    }

    private void addFoodChainUpdateMethodMock() {
        doAnswer(invocationOnMock -> {
            FoodChain fd = invocationOnMock.getArgumentAt(0, FoodChain.class);
            if (fd == null || fd.getAnimals() == null) {
                throw new IllegalArgumentException("FoodChain animals cannot be null.");
            }
            return fd;
        }).when(foodChainDao).updateFoodChain(any(FoodChain.class));
    }

    private void addGetStandardFoodChainByIdMethodMock() {
        Mockito.when(foodChainDao.getFoodChain(standardFoodChain.getId())).thenReturn(standardFoodChain);
    }
}


