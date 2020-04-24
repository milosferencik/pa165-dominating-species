package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.dto.AnimalInFoodChainDTO;
import cz.muni.fi.dto.FoodChainDTO;
import cz.muni.fi.dto.FoodChainCreateDTO;
import java.util.List;

public interface FoodChainFacade {

    List<FoodChainDTO> getFoodChainsWithAnimal(Long animalId);
    List<FoodChainDTO> getAllFoodChains();
    FoodChainDTO getFoodChainById(Long id);
    void updateFoodChain(FoodChainDTO foodChainDTO);
    void deleteFoodChain(Long id);
    void addAnimalToBeginning(AnimalDTO animalDTO, Long id);
    void addAnimalToEnd(AnimalDTO animalDTO, Long id);
    void removeAnimal(AnimalInFoodChainDTO animalInFoodChain);
    Long createFoodChain(FoodChainCreateDTO foodChainCreateDTO);
}
