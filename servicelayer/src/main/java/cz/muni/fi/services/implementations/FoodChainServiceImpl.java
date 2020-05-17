package cz.muni.fi.services.implementations;

import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.AnimalInFoodChain;
import dao.entities.FoodChain;
import dao.interfaces.FoodChainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @autor Milos Ferencik on 18/04/2020.
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
    public void deleteFoodChain(Long id) throws DataAccessException {
        try {
            foodChainDao.deleteFoodChain(id);
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
    public void addAnimalToBeginningOfFoodChain(Animal animal, Long id) {
        FoodChain foodChain = getFoodChain(id);
        if (foodChain == null)
            throw new ServiceDataAccessException("FoodChain with the id doesn't exist.");

        List<AnimalInFoodChain> animals = foodChain.getAnimalsInFoodChain();
        int index = 1;

        AnimalInFoodChain tmp = new AnimalInFoodChain();
        tmp.setAnimal(animal);
        tmp.setFoodChain(foodChain);
        tmp.setIndexInFoodChain(index);
        animals.add(0, tmp);

        for (AnimalInFoodChain animalInFoodChain : animals) {
            animalInFoodChain.setIndexInFoodChain(index);
            ++index;
        }

        updateFoodChain(foodChain);
    }

    @Override
    public void addAnimalToEndOfFoodChain(Animal animal, Long id) {
        FoodChain foodChain = getFoodChain(id);
        if (foodChain == null)

            throw new ServiceDataAccessException("FoodChain with the id doesn't exist.");
        List<AnimalInFoodChain> animals = foodChain.getAnimalsInFoodChain();
        int index = 1;

        AnimalInFoodChain tmp = new AnimalInFoodChain();
        tmp.setAnimal(animal);
        tmp.setFoodChain(foodChain);
        tmp.setIndexInFoodChain(index);
        animals.add(tmp);

        for (AnimalInFoodChain animalInFoodChain : animals) {
            animalInFoodChain.setIndexInFoodChain(index);
            ++index;
        }
        updateFoodChain(foodChain);
    }


    @Override
    public  void removeAnimal(AnimalInFoodChain animalInFoodChain) {
        if (animalInFoodChain == null)
            throw new ServiceDataAccessException("AnimalInFoodChain cannot be null");

        if (animalInFoodChain.getFoodChain() == null)
            throw new ServiceDataAccessException("FoodChain in the animal-foodchain association cannot be null");

        if (animalInFoodChain.getAnimal() == null)
            throw new ServiceDataAccessException("Animal in the animal-foodchain association cannot be null");

        FoodChain foodChain = animalInFoodChain.getFoodChain();
        List<AnimalInFoodChain> animalsInFoodChain = foodChain.getAnimalsInFoodChain();
        animalsInFoodChain.remove(animalInFoodChain);
        if (animalsInFoodChain.size() < 2) {
            deleteFoodChain(foodChain.getId());
        } else {
            foodChain.setAnimalsInFoodChain(animalsInFoodChain);
            updateFoodChain(foodChain);
        }
    }
}
