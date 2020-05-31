package cz.muni.fi.pa165.dominatingspecies.dao.interfaces;

import cz.muni.fi.pa165.dominatingspecies.dao.entities.Animal;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.AnimalInFoodChain;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.FoodChain;

import java.util.List;

/**
 * @author Ondrej Slimak on 25/03/2020.
 */
public interface FoodChainDao {

    /**
     * Stores new FoodChain
     * @param foodChain to be created
     */
    void createFoodChain(FoodChain foodChain);

    /**
     * Finds all FoodChains
     * @return List of all FoodChains
     */
    List<FoodChain> getAllFoodChains();

    /**
     * Finds FoodChain by id
     * @param id of FoodChain
     * @return FoodChain or null, if there is no FoodChain with given id
     */
    FoodChain getFoodChain(Long id);

    /**
     * Updates existing FoodChain
     * @param foodChain to be updated
     */
    void updateFoodChain(FoodChain foodChain);

    /**
     * Removes FoodChain
     * @param id of foodChain to be deleted
     */
    void deleteFoodChain(Long id);

    /**
     * Removes animal from food chain
     * @param animalInFoodChain object to be removed,
     */
    void removeAnimalFromFoodChain(AnimalInFoodChain animalInFoodChain);

    /**
     * Finds foodChains with animal
     * @param animal which foodChains contain
     * @return
     */
    List<FoodChain> getFoodChainsWithAnimal(Animal animal);
}
