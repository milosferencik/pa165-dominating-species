package cz.muni.fi.services.interfaces;

import dao.entities.Animal;
import dao.entities.FoodChain;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by Milos Ferencik on 17/04/2020.
 */
public interface FoodChainService {
    /**
     * Create new entity in the database
     * @param foodChain to be created
     */
    void createFoodChain(FoodChain foodChain) throws DataAccessException;

    /**
     * Finds all entities in the database
     * @return List of all FoodChains
     */
    List<FoodChain> getAllFoodChains() throws DataAccessException;

    /**
     * Find entity with given id in the database
     * @param id of FoodChain
     * @return FoodChain or null, if there is no FoodChain with given id
     */
    FoodChain getFoodChain(Long id) throws DataAccessException;

    /**
     * Update entity in the database
     * @param foodChain to be updated
     */
    void updateFoodChain(FoodChain foodChain) throws DataAccessException;

    /**
     * Delete entity from the database
     * @param foodChain to be deleted
     */
    void deleteFoodChain(FoodChain foodChain) throws DataAccessException;

    /**
     * Finds foodChains entities with animal
     * @param animal which foodChains contain
     * @return
     */
    public List<FoodChain> getFoodChainsWithAnimal(Animal animal) throws DataAccessException;;

    /**
     * Add the animal to the left in the foodChain
     * @param animal to be added
     * @param id of foodChain
     */
    public void addAnimalToLeft(Animal animal, Long id) throws DataAccessException;;

    /**
     * Add the animal to the right in the foodChain
     * @param animal to be added
     * @param id of foodChain
     */
    public void addAnimalToRight(Animal animal, Long id) throws DataAccessException;;

    /**
     * Remove the animal from the foodChain
     * if animal is not on the edge, two foodChains will arise if the conditions are met
     * @param animal to be removed
     * @param id of foodChain
     */
    public void removeAnimal(Animal animal, Long id) throws DataAccessException;;
}
