package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalListDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.FoodWebDTO;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.entities.FoodChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class FoodWebFacadeImpl implements FoodWebFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private FoodChainService foodChainService;

    @Autowired
    private AnimalService animalService;

    @Override
    public FoodWebDTO getFoodWebFromAllFoodChains() {
        FoodWebDTO result = new FoodWebDTO();
        Map<AnimalListDTO, List<AnimalListDTO>> foodWeb = buildFoodWeb(animalService.findAll());
        result.setFoodWeb(foodWeb);
        return result;
    }

    @Override
    public FoodWebDTO getFoodWebByEnvironment(EnvironmentDTO environment) {
        List<Animal> animals = animalService.findAnimalsByEnvironment(beanMappingService.mapTo(environment, Environment.class));
        Map<AnimalListDTO, List<AnimalListDTO>> foodWeb = buildFoodWeb(animals);
        
        FoodWebDTO result = new FoodWebDTO();
        result.setFoodWeb(foodWeb);
        return result;
    }
    
    @Override
    public FoodWebDTO getFoodWebByAnimal(AnimalListDTO animal){
        FoodWebDTO result = new FoodWebDTO();

        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(beanMappingService.mapTo(animal, Animal.class));

        Map<AnimalListDTO, List<AnimalListDTO>> foodWeb = buildFoodWeb(animals);
        result.setFoodWeb(foodWeb);
        return result;
    }
    
    private Map<AnimalListDTO, List<AnimalListDTO>> buildFoodWeb(List<Animal> animals) {
        Map<AnimalListDTO, List<AnimalListDTO>> foodWeb = new HashMap<>();
        
        for (Animal animal : animals) {
            List<FoodChain> foodChainsOfAnimal = foodChainService.getFoodChainsWithAnimal(animal);

            List<Animal> preys = new ArrayList<>();
            for (FoodChain fd : foodChainsOfAnimal) {
                List<Animal> animalsOfFoodChain = fd.getAnimals();
                int indexOfAnimal = animalsOfFoodChain.indexOf(animal);
                if (indexOfAnimal > 0){
                    preys.add(animalsOfFoodChain.get(indexOfAnimal));
                }
            }
            foodWeb.put(beanMappingService.mapTo(animal, AnimalListDTO.class), beanMappingService.mapTo(preys, AnimalListDTO.class));
        }
        return foodWeb;
    }

}
