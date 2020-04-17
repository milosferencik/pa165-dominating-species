package cz.muni.fi.services.interfaces;

import dao.entities.FoodChain;

import java.util.List;

/**
 * Created by Milos Ferencik on 17/04/2020.
 */
public interface FoodChainService {
    /**
     * Create new entity in the database
     * @param foodChain to be created
     */
    void createFoodChain(FoodChain foodChain);

    /**
     * Finds all entities in the database
     * @return List of all FoodChains
     */
    List<FoodChain> getAllFoodChains();

    /**
     * Find entity with given id in the database
     * @param id of FoodChain
     * @return FoodChain or null, if there is no FoodChain with given id
     */
    FoodChain getFoodChain(Long id);

    /**
     * Update entity in the database
     * @param foodChain to be updated
     */
    void updateFoodChain(FoodChain foodChain);

    /**
     * Delete entity from the database
     * @param foodChain to be deleted
     */
    void deleteFoodChain(FoodChain foodChain);
}
