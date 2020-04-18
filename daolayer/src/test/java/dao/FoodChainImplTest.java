package dao;

/**
 * Created by Kostka on 28/03/2020.
 */
import dao.config.MainConfiguration;
import dao.entities.Animal;
import dao.entities.AnimalInFoodChain;
import dao.entities.Environment;
import dao.entities.FoodChain;
import dao.interfaces.FoodChainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@ContextConfiguration(classes = MainConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class FoodChainImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private FoodChainDao foodChainDao;

    @PersistenceContext
    private EntityManager entityManager;

    private FoodChain foodChain1;
    private FoodChain foodChain2;
    private Animal vole;
    private Animal fox;

    private Animal fox;

    @BeforeMethod
    public void setUp() throws Exception {

        Environment field = new Environment();
        field.setName("Field");
        field.setDescription("Regular field");

        entityManager.persist(field);

        Animal insect = new Animal();
        insect.setName("Insect");
        insect.setSpecies("Grasshopper");
        insect.setEnvironment(field);

        vole = new Animal();
        vole.setName("Vole");
        vole.setSpecies("Bank Vole");
        vole.setEnvironment(field);

        Animal hawk = new Animal();
        hawk.setName("Hawk");
        hawk.setSpecies("Red-tailed Hawk");
        hawk.setEnvironment(field);

        fox = new Animal();
        fox.setName("Fox");
        fox.setSpecies("Red Fox");
        fox.setEnvironment(field);


        entityManager.persist(insect);
        entityManager.persist(vole);
        entityManager.persist(hawk);
        entityManager.persist(fox);


        foodChain1 = new FoodChain();
        foodChain2 = new FoodChain();
        List<Animal> foodChainList1 = new ArrayList<>();
        List<Animal> foodChainList2 = new ArrayList<>();

        foodChainList1.add(insect);
        foodChainList1.add(vole);
        foodChainList1.add(hawk);

        foodChainList2.add(insect);
        foodChainList2.add(vole);
        foodChainList2.add(fox);

        foodChain1.setAnimals(foodChainList1);
        foodChain2.setAnimals(foodChainList2);
    }

    @Test
    public void testCreateSingleFoodChain() {
        assertThat(foodChainDao.getAllFoodChains()).hasSize(0);
        foodChainDao.createFoodChain(foodChain1);
        assertThat(foodChainDao.getAllFoodChains()).hasSize(1);
    }

    @Test
    public void testCreateMultipleFoodChain() {
        assertThat(foodChainDao.getAllFoodChains()).hasSize(0);
        foodChainDao.createFoodChain(foodChain1);
        assertThat(foodChainDao.getAllFoodChains()).hasSize(1);
        foodChainDao.createFoodChain(foodChain2);
        assertThat(foodChainDao.getAllFoodChains()).hasSize(2);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreateFoodChainWithId() {
        foodChain1.setId(1L);
        foodChainDao.createFoodChain(foodChain1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateFoodChainWithNullList() {
        foodChain1.setAnimalsInFoodChain(null);
        foodChainDao.createFoodChain(foodChain1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateFoodChainWithEmptyList() {
        foodChain1.setAnimalsInFoodChain(new ArrayList<>());
        foodChainDao.createFoodChain(foodChain1);
    }


    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreateFoodChainWithNonExistingAnimals() {
        FoodChain foodChain = new FoodChain();
        List<Animal> noneExistingAnimalList = foodChain2.getAnimals();

        Animal nonExistingAnimal = new Animal();
        nonExistingAnimal.setId(999L);
        nonExistingAnimal.setName("NotExisting");
        nonExistingAnimal.setEnvironment(noneExistingAnimalList.get(0).getEnvironment());
        nonExistingAnimal.setSpecies("None");

        noneExistingAnimalList.add(nonExistingAnimal);

        foodChain.setAnimals(noneExistingAnimalList);
        foodChainDao.createFoodChain(foodChain);
    }


    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateFoodChainWithOneExistingAnimal() {
        FoodChain foodChain = new FoodChain();
        List<Animal> animals = new ArrayList<>();
        animals.add(fox);
        foodChain.setAnimals(animals);
        foodChainDao.createFoodChain(foodChain);
    }

    @Test
    public void testGetAllFoodChainsOnEmptyDb(){
        assertThat(foodChainDao.getAllFoodChains()).isEmpty();
    }

    @Test
    public void testGetAllFoodChainsOnDbWithMultipleFoodChains() {
        foodChainDao.createFoodChain(foodChain1);
        foodChainDao.createFoodChain(foodChain2);

        assertThat(foodChainDao.getAllFoodChains()).hasSize(2);
    }

    @Test
    public void testGetFoodChainByIdCorrectly() {
        foodChainDao.createFoodChain(foodChain1);
        foodChainDao.createFoodChain(foodChain2);

        assertThat(foodChainDao.getFoodChain(foodChain1.getId())).isEqualToComparingFieldByFieldRecursively(foodChain1);
        assertThat(foodChainDao.getFoodChain(foodChain2.getId())).isEqualToComparingFieldByFieldRecursively(foodChain2);
    }

    @Test
    public void testGetFoodChainByNonExistingId() {
        assertThat(foodChainDao.getFoodChain(666L)).isNull();
    }


    @Test(expectedExceptions = DataAccessException.class)
    public void testGetFoodChainByNullId() {
        foodChainDao.getFoodChain(null);
    }

    @Test
    public void testUpdateFoodChainCorrectly() {
        foodChainDao.createFoodChain(foodChain2);
        entityManager.flush();
        entityManager.detach(foodChain2);

        List<Animal> foodChainList2Updated = foodChain2.getAnimals();
        foodChainList2Updated.remove(0);

        foodChain2.setAnimals(foodChainList2Updated);
        foodChainDao.updateFoodChain(foodChain2);

        assertThat(foodChainDao.getFoodChain(foodChain2.getId())).isEqualToComparingFieldByFieldRecursively(foodChain2);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateFoodChainWithNull() {
        foodChainDao.createFoodChain(foodChain1);
        foodChain1.setAnimalsInFoodChain(null);
        foodChainDao.updateFoodChain(foodChain1);
        entityManager.flush();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateFoodChainWithEmptyList() {
        foodChainDao.createFoodChain(foodChain1);
        foodChain1.setAnimalsInFoodChain(new ArrayList<>());
        foodChainDao.updateFoodChain(foodChain1);
        entityManager.flush();
    }


    @Test
    public void testUpdateFoodChainWithNonExistingAnimals() {
        List<Animal> noneExistingAnimalList = foodChain1.getAnimals();

        foodChainDao.createFoodChain(foodChain1);
        entityManager.flush();
        entityManager.detach(foodChain1);

        Animal nonExistingAnimal = new Animal();
        nonExistingAnimal.setName("NotExisting");
        nonExistingAnimal.setEnvironment(noneExistingAnimalList.get(0).getEnvironment());
        nonExistingAnimal.setSpecies("None");

        noneExistingAnimalList.add(nonExistingAnimal);

        foodChain1.setAnimals(noneExistingAnimalList);
        foodChainDao.updateFoodChain(foodChain1);
        entityManager.flush();
    }

    @Test
    public void testDeleteFoodChainCorrectly() {
        foodChainDao.createFoodChain(foodChain1);
        assertThat(foodChainDao.getAllFoodChains()).isEqualTo(Collections.singletonList(foodChain1));
        foodChainDao.deleteFoodChain(foodChain1);
        assertThat(foodChainDao.getAllFoodChains()).isEmpty();
    }

    @Test
    public void testDeleteNonExistingFoodChain() {
        foodChainDao.createFoodChain(foodChain1);
        assertThat(foodChainDao.getAllFoodChains()).isEqualTo(Collections.singletonList(foodChain1));
        foodChainDao.deleteFoodChain(foodChain2);
        assertThat(foodChainDao.getAllFoodChains()).isEqualTo(Collections.singletonList(foodChain1));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNullFoodChain() {
        foodChainDao.deleteFoodChain(null);
    }

    @Test
    public void testGetFoodChainsWithAnimal() {
        foodChainDao.createFoodChain(foodChain1);
        foodChainDao.createFoodChain(foodChain2);
        List<FoodChain> result1 = foodChainDao.getFoodChainsWithAnimal(fox);
        assertThat(result1).hasSize(1);
        assertThat(result1.get(0)).isEqualToComparingFieldByField(foodChain2);

        List<FoodChain> result2 = foodChainDao.getFoodChainsWithAnimal(vole);
        assertThat(result2).hasSize(2);
    }

}
