package cz.muni.fi.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Katarina Matusova
 */
public class FoodChainDTO {

    private Long id;
    private List<AnimalInFoodChainDTO> animalsInFoodChain = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnimalsInFoodChain(List<AnimalInFoodChainDTO> animalsInFoodChain) {
        this.animalsInFoodChain = animalsInFoodChain;
    }

    public Long getId() {
        return id;
    }

    public List<AnimalInFoodChainDTO> getAnimalsInFoodChain() {
        return animalsInFoodChain;
    }
    public void setAnimals(List<AnimalListDTO> animals) {
        if (animals == null)
            throw new IllegalArgumentException("Animals cannot be null.");

        this.animalsInFoodChain.clear();

        for (int i = 0; i < animals.size(); i++) {
            AnimalListDTO animal = animals.get(i);
            AnimalInFoodChainDTO tmp = new AnimalInFoodChainDTO();
            tmp.setAnimal(animal);
            tmp.setIndexInFoodChain(i);

            this.animalsInFoodChain.add(tmp);
        }
    }

    public List<AnimalListDTO> getAnimals() {
        List<AnimalListDTO> result = new ArrayList<>();
        for (AnimalInFoodChainDTO animalInFoodChain : animalsInFoodChain) {
            result.add(animalInFoodChain.getAnimal());
        }
        return result;
    }
}
