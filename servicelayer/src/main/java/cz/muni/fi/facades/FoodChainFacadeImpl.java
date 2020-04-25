package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.dto.AnimalInFoodChainDTO;
import cz.muni.fi.dto.FoodChainCreateDTO;
import cz.muni.fi.dto.FoodChainDTO;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.AnimalInFoodChain;
import dao.entities.Environment;
import dao.entities.FoodChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Katarina Matusova
 */
@Service
@Transactional
public class FoodChainFacadeImpl implements FoodChainFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private FoodChainService foodChainService;

    @Autowired
    private AnimalService animalService;


    @Override
    public List<FoodChainDTO> getFoodChainsWithAnimal(Long animalId) {
        Animal animal = animalService.getAnimal(animalId);
        List<FoodChain> foodChains = foodChainService.getFoodChainsWithAnimal(animal);
        return beanMappingService.mapTo(foodChains, FoodChainDTO.class);
    }

    @Override
    public List<FoodChainDTO> getAllFoodChains() {
        return beanMappingService.mapTo(foodChainService.getAllFoodChains(), FoodChainDTO.class);
    }

    @Override
    public FoodChainDTO getFoodChainById(Long id) {
        FoodChain foodChain = foodChainService.getFoodChain(id);
        return (foodChain == null) ? null : beanMappingService.mapTo(foodChain, FoodChainDTO.class);
    }

    @Override
    public void updateFoodChain(FoodChainDTO foodChainDTO) {
        foodChainService.updateFoodChain(beanMappingService.mapTo(foodChainDTO, FoodChain.class));
    }

    @Override
    public void deleteFoodChain(Long id) {
        FoodChain foodChain = new FoodChain();
        foodChain.setId(id);
        foodChainService.deleteFoodChain(foodChain);
    }

    @Override
    public void addAnimalToBeginning(AnimalDTO animalDTO, Long id) {
        Animal animal= beanMappingService.mapTo(animalDTO, Animal.class);
        foodChainService.addAnimalToBeginningOfFoodChain(animal,id);
    }

    @Override
    public void addAnimalToEnd(AnimalDTO animalDTO, Long id) {
        Animal animal= beanMappingService.mapTo(animalDTO, Animal.class);
        foodChainService.addAnimalToEndOfFoodChain(animal,id);
    }

    @Override
    public void removeAnimal(AnimalInFoodChainDTO animalInFoodChainDTO) {
        foodChainService.removeAnimal(beanMappingService.mapTo(animalInFoodChainDTO, AnimalInFoodChain.class));
    }

    @Override
    public Long createFoodChain(FoodChainCreateDTO foodChainCreateDTO) {
        FoodChain mappedFoodChain = beanMappingService.mapTo(foodChainCreateDTO, FoodChain.class);
        foodChainService.createFoodChain(mappedFoodChain);
        return mappedFoodChain.getId();
    }
}
