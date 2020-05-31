package cz.muni.fi.pa165.dominatingspecies.api.facades;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalInFoodChainDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainDTO;

import java.util.List;

/**
 * Facade responsible for foodChain handling
 *
 * @author Katarina Matusova
 */
public interface FoodChainFacade {

    /**
     * Finds foodChains with animal
     * @param animalId which foodChains contain
     * @return List of FoodChainDTO
     */
    List<FoodChainDTO> getFoodChainsWithAnimal(Long animalId);

    /**
     * Finds all foodChains
     * @return List of FoodChainDTO
     */
    List<FoodChainDTO> getAllFoodChains();

    /**
     * Finds foodChain by its id
     * @param id of the foodChain
     * @return desired foodChain with id
     */
    FoodChainDTO getFoodChainById(Long id);

    /**
     * Updates foodChain
     * @param foodChainDTO given information about foodChain used for update
     */
    void updateFoodChain(FoodChainDTO foodChainDTO);

    /**
     * Deletes foodChain
     * @param id of foodChain to be deleted
     */
    void deleteFoodChain(Long id);

    /**
     * Add the animal to the left in the foodChain
     * @param animalDTO animal to be added
     * @param id id of foodChain
     */
    void addAnimalToBeginning(AnimalDTO animalDTO, Long id);

    /**
     * Add the animal to the right in the foodChain
     * @param animalDTO animal to be added
     * @param id id of foodChain
     */
    void addAnimalToEnd(AnimalDTO animalDTO, Long id);

    /**
     * Removes animal from food chain
     * @param animalInFoodChain animal in association FoodChain-Animal to be removed
     */
    void removeAnimal(AnimalInFoodChainDTO animalInFoodChain);

    /**
     * Creates new food chain
     * @param foodChainCreateDTO DTO with necessary attributes of foodChain
     * @return id of the created foodChain
     */
    Long createFoodChain(FoodChainCreateDTO foodChainCreateDTO);
}
