package cz.muni.fi.services.implementations;

import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.FoodChain;
import dao.interfaces.FoodChainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Milos Ferencik on 18/04/2020.
 */
@Service
public class FoodChainServiceImpl implements FoodChainService {
    @Autowired
    private FoodChainDao foodChainDao;

    @Override
    public void createFoodChain(FoodChain foodChain) throws DataAccessException {
        try {
            foodChainDao.createFoodChain(foodChain);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not create foodChain.", e);
        }
    }

    @Override
    public List<FoodChain> getAllFoodChains() throws DataAccessException {
        try {
            return foodChainDao.getAllFoodChains();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not get all foodChains.", e);
        }
    }

    @Override
    public FoodChain getFoodChain(Long id) throws DataAccessException {
        try {
            return foodChainDao.getFoodChain(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Error when finding foodChain.", e);
        }
    }

    @Override
    public void updateFoodChain(FoodChain foodChain) throws DataAccessException {
        try {
            foodChainDao.updateFoodChain(foodChain);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not update foodChain.", e);
        }
    }

    @Override
    public void deleteFoodChain(FoodChain foodChain) throws DataAccessException {
        try {
            foodChainDao.deleteFoodChain(foodChain);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not delete foodChain.", e);
        }
    }

    @Override
    public List<FoodChain> getFoodChainsWithAnimal(Animal animal) {
        try {
            return foodChainDao.getFoodChainsWithAnimal(animal);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find foodChains with animal.", e);
        }
    }

    @Override
    public void addAnimalToLeft(Animal animal, Long id) {
        FoodChain foodChain = getFoodChain(id);
        if (foodChain == null)
            throw new ServiceDataAccessException("FoodChain with the id doesn't exist.");
        List<Animal> animals = foodChain.getAnimals();
        animals.add(0, animal);
        foodChain.setAnimals(animals);
        updateFoodChain(foodChain);
    }

    @Override
    public void addAnimalToRight(Animal animal, Long id) {
        FoodChain foodChain = getFoodChain(id);
        if (foodChain == null)
            throw new ServiceDataAccessException("FoodChain with the id doesn't exist.");
        List<Animal> animals = foodChain.getAnimals();
        animals.add(animal);
        foodChain.setAnimals(animals);
        updateFoodChain(foodChain);
    }

    @Override
    public void removeAnimal(Animal animal, Long id) {
        FoodChain foodChain = getFoodChain(id);
        if (foodChain == null)
            throw new ServiceDataAccessException("FoodChain with the id doesn't exist.");
        List<Animal> animals = foodChain.getAnimals();
        int indexOfRemovedAnimal = animals.indexOf(animal);
        if (indexOfRemovedAnimal == -1)
            throw new ServiceDataAccessException("FoodChain doesn't contain the animal.");
        List<Animal> animalsBeforeRemovedAnimal = animals.subList(0,indexOfRemovedAnimal);
        List<Animal> animalsAfterRemovedAnimal = animals.subList(indexOfRemovedAnimal + 1, animals.size());

        if (animalsBeforeRemovedAnimal.size() < 2 && animalsAfterRemovedAnimal.size() < 2)
        {
            deleteFoodChain(foodChain);
        }
        else if (animalsBeforeRemovedAnimal.size() > 2 && animalsAfterRemovedAnimal.size() < 2 ){
            foodChain.setAnimals(animalsBeforeRemovedAnimal);
            updateFoodChain(foodChain);
        }
        else if (animalsBeforeRemovedAnimal.size() < 2 && animalsAfterRemovedAnimal.size() > 2 ){
            foodChain.setAnimals(animalsAfterRemovedAnimal);
            updateFoodChain(foodChain);
        }
        else {
            foodChain.setAnimals(animalsBeforeRemovedAnimal);
            updateFoodChain(foodChain);
            FoodChain newFoodChain = new FoodChain();
            newFoodChain.setAnimals(animalsAfterRemovedAnimal);
            createFoodChain(newFoodChain);
        }
    }
}
