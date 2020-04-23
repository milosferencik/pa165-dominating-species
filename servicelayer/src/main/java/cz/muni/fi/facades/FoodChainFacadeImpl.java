package cz.muni.fi.facades;

import cz.muni.fi.dto.FoodChainDTO;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.FoodChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FoodChainFacadeImpl implements FoodChainFacade {
    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private FoodChainService foodChainService;

    @Autowired
    private AnimalService animalService;


    @Override
    public List<FoodChainDTO> getFoodChainsWithAnimal(Long animalId) {
        Animal animal = animalService.findById(animalId);
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
}
