package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalDTO;
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
import java.util.*;

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
