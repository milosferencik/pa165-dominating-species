package dao.interfaces;

import dao.entities.FoodChain;

import java.util.List;

/**
 * Created by Ondrej Slimak on 25/03/2020.
 */
public interface FoodChainDao {

    public void createFoodChain(FoodChain foodChain);
    public List<FoodChain> getAllFoodChains();
    public FoodChain getFoodChain(Long id);
    public void updateFoodChain(FoodChain foodChain);
    public void deleteFoodChain(FoodChain foodChain);
}
