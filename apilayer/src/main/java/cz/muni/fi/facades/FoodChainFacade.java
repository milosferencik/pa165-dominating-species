package cz.muni.fi.facades;

import cz.muni.fi.dto.FoodChainDTO;
import java.util.List;

public interface FoodChainFacade {

    List<FoodChainDTO> getFoodChainsWithAnimal(Long animalId);
    List<FoodChainDTO> getAllFoodChains();
    FoodChainDTO getFoodChainById(Long id);
}
