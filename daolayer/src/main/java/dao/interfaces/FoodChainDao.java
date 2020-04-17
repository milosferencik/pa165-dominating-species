package dao.interfaces;

import dao.entities.FoodChain;

import java.util.List;

/**
 * Created by Ondrej Slimak on 25/03/2020.
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
     * @param foodChain to be deleted
     */
    void deleteFoodChain(FoodChain foodChain);
}
