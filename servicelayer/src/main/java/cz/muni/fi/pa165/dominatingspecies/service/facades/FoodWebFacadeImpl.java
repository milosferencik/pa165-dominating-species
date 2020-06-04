package cz.muni.fi.pa165.dominatingspecies.service.facades;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalListDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodWebDTO;
import cz.muni.fi.pa165.dominatingspecies.api.facades.FoodWebFacade;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.AnimalService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.BeanMappingService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.FoodChainService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Animal;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.FoodChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author  Ondrej Slimak on 24/04/2020.
 */

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
        Map<AnimalListDTO, List<AnimalListDTO>> foodWeb = buildFoodWeb(animalService.getAllAnimals());
        result.setFoodWeb(foodWeb);
        return result;
    }

    @Override
    public FoodWebDTO getFoodWebByEnvironment(EnvironmentDTO environment) {
        List<Animal> animals = animalService.getAnimalsByEnvironment(beanMappingService.mapTo(environment, Environment.class));
        Map<AnimalListDTO, List<AnimalListDTO>> foodWeb = buildFoodWeb(animals);
        
        FoodWebDTO result = new FoodWebDTO();
        result.setFoodWeb(foodWeb);
        return result;
    }
    
    @Override
    public FoodWebDTO getFoodWebByAnimal(AnimalDTO animal){
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

            HashSet<Animal> preys = new HashSet<>();
            for (FoodChain fd : foodChainsOfAnimal) {
                List<Animal> animalsOfFoodChain = fd.getAnimals();
                List<Integer> indexesOfAnimal = indexOfAllAnimalOccurrencesInList(animal, animalsOfFoodChain);

                for (Integer index : indexesOfAnimal) {
                    if (index > 0){
                        preys.add(animalsOfFoodChain.get(index - 1));
                    }
                }
            }
            foodWeb.put(beanMappingService.mapTo(animal, AnimalListDTO.class), beanMappingService.mapTo(preys, AnimalListDTO.class));
        }
        return foodWeb;
    }

    private List<Integer> indexOfAllAnimalOccurrencesInList(Animal animal, List<Animal> list) {
        final List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (animal.equals(list.get(i))) {
                indexList.add(i);
            }
        }
        return indexList;
    }

}
