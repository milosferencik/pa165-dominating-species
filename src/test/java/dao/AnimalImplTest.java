package dao;

import dao.config.MainConfiguration;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.interfaces.AnimalDao;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;


import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ondrej Slimak
 */

@ContextConfiguration(classes = MainConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AnimalImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AnimalDao animalDao;

    @PersistenceContext
    private EntityManager em;

    private Animal lion;
    private Animal flamingo;
    private Animal hamster;

    @BeforeMethod
    public void setUp() {
        Environment anywhere = new Environment();
        anywhere.setName("Anywhere");
        anywhere.setDescription("Literally anywhere...");

        Environment anywhereElse = new Environment();
        anywhereElse.setName("Anywhere else");
        anywhereElse.setDescription("Anywhere except for the 'Anywhere' environments.");

        em.persist(anywhere);
        em.persist(anywhereElse);

        lion = new Animal();
        lion.setName("Lion");
        lion.setSpecies("Panthera leo");
        lion.setEnvironment(anywhere);

        flamingo = new Animal();
        flamingo.setName("Flamingo");
        flamingo.setSpecies("Chilean flamingo");
        flamingo.setEnvironment(anywhere);

        hamster = new Animal();
        hamster.setName("Hamster");
        hamster.setSpecies("Chinese Hamster");
        hamster.setEnvironment(anywhereElse);
    }


    @Test
    public void testCreateCorrectAnimalSingle() {
        assertThat(animalDao.getAllAnimals()).hasSize(0);

        animalDao.createAnimal(lion);
        assertThat(animalDao.getAllAnimals()).hasSize(1);
    }

    @Test
    public void testCreateCorrectAnimalMultiple() {
        assertThat(animalDao.getAllAnimals()).hasSize(0);

        animalDao.createAnimal(lion);
        assertThat(animalDao.getAllAnimals()).hasSize(1);

        animalDao.createAnimal(flamingo);
        assertThat(animalDao.getAllAnimals()).hasSize(2);

        animalDao.createAnimal(hamster);
        assertThat(animalDao.getAllAnimals()).hasSize(3);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreateAnimalWithId() {
        lion.setId(1L);
        animalDao.createAnimal(lion);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateAnimalWithNullName() {
        lion.setName(null);
        animalDao.createAnimal(lion);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateAnimalWithEmptyName() {
        lion.setName("");
        animalDao.createAnimal(lion);
    }

    @Test(expectedExceptions = javax.validation.ConstraintViolationException.class)
    public void testCreateAnimalWithNullSpecies() {
        lion.setSpecies(null);
        animalDao.createAnimal(lion);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateAnimalWithEmptySpecies() {
        lion.setSpecies("");
        animalDao.createAnimal(lion);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateAnimalWithNullEnvironment() {
        lion.setEnvironment(null);
        animalDao.createAnimal(lion);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreateAnimalWithNonExistingEnvironment() {
        Environment nonExistingEnvironment = new Environment();
        nonExistingEnvironment.setId(999L);
        nonExistingEnvironment.setName("NotExisting");
        nonExistingEnvironment.setDescription("This environment does not exist in Db");
        lion.setEnvironment(nonExistingEnvironment);

        animalDao.createAnimal(lion);
    }

    @Test
    public void testGetAllAnimalsOnEmptyDb(){
        assertThat(animalDao.getAllAnimals()).isEmpty();
    }

    @Test
    public void testGetAllAnimalsOnDbWithMultipleAnimals() {
        animalDao.createAnimal(lion);
        animalDao.createAnimal(flamingo);
        animalDao.createAnimal(hamster);

        assertThat(animalDao.getAllAnimals()).hasSize(3);
    }

    @Test
    public void testGetAnimalByIdCorrectly() {
        animalDao.createAnimal(lion);
        animalDao.createAnimal(flamingo);
        animalDao.createAnimal(hamster);

        assertThat(animalDao.getAnimal(lion.getId())).isEqualToComparingFieldByFieldRecursively(lion);
        assertThat(animalDao.getAnimal(flamingo.getId())).isEqualToComparingFieldByFieldRecursively(flamingo);
        assertThat(animalDao.getAnimal(hamster.getId())).isEqualToComparingFieldByFieldRecursively(hamster);
    }

    @Test
    public void testGetAnimalByNonExistingId() {
        assertThat(animalDao.getAnimal(666L)).isNull();
    }


    @Test(expectedExceptions = DataAccessException.class)
    public void testGetAnimalByNullId() {
        animalDao.getAnimal(null);
    }

    @Test
    public void testUpdateAnimalCorrectly() {
        animalDao.createAnimal(lion);

        lion.setName("Big Lion");
        lion.setSpecies("Panthera leo Mosquito");
        animalDao.updateAnimal(lion);

        assertThat(animalDao.getAnimal(lion.getId())).isEqualToComparingFieldByFieldRecursively(lion);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testUpdateAnimalWithNullName() {
        animalDao.createAnimal(lion);
        lion.setName(null);
        animalDao.updateAnimal(lion);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateAnimalWithEmptyName() {
        animalDao.createAnimal(lion);
        lion.setName("");
        animalDao.updateAnimal(lion);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateAnimalWithNullSpecies() {
        animalDao.createAnimal(lion);
        lion.setSpecies(null);
        animalDao.updateAnimal(lion);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateAnimalWithEmptySpecies() {
        animalDao.createAnimal(lion);
        lion.setSpecies("");
        animalDao.updateAnimal(lion);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateAnimalWithNullEnvironment() {
        animalDao.createAnimal(lion);
        lion.setEnvironment(null);
        animalDao.createAnimal(lion);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void testUpdateAnimalWithNonExistingEnvironment() {
        animalDao.createAnimal(lion);

        Environment nonExistingEnvironment = new Environment();
        nonExistingEnvironment.setId(999L);
        nonExistingEnvironment.setName("NotExisting");
        nonExistingEnvironment.setDescription("This environment does not exist in Db");
        lion.setEnvironment(nonExistingEnvironment);

        animalDao.createAnimal(lion);
    }

    @Test
    public void testDeleteAnimalCorrectly() {
        animalDao.createAnimal(lion);
        assertThat(animalDao.getAllAnimals()).isEqualTo(Collections.singletonList(lion));
        animalDao.deleteAnimal(lion);
        assertThat(animalDao.getAllAnimals()).isEmpty();
    }

    @Test
    public void testDeleteNonExistingAnimal() {
        animalDao.createAnimal(lion);
        assertThat(animalDao.getAllAnimals()).isEqualTo(Collections.singletonList(lion));
        animalDao.deleteAnimal(hamster);
        assertThat(animalDao.getAllAnimals()).isEqualTo(Collections.singletonList(lion));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNullAnima() {
        animalDao.deleteAnimal(null);
    }
}
