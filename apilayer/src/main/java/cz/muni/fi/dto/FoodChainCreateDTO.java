package cz.muni.fi.dto;

import java.util.List;
import java.util.Objects;

/**
 * DTO for FoodChain - create
 * @author Katarina Matusova
 */
public class FoodChainCreateDTO {

    private List<AnimalInFoodChainDTO> animalsInFoodChain;

    public List<AnimalInFoodChainDTO> getAnimalsInFoodChain() {
        return animalsInFoodChain;
    }

    public void setAnimalsInFoodChain(List<AnimalInFoodChainDTO> animalsInFoodChain) {
        this.animalsInFoodChain = animalsInFoodChain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodChainCreateDTO)) return false;
        FoodChainCreateDTO that = (FoodChainCreateDTO) o;

        // Hibernate BUG in PersistentBag: https://hibernate.atlassian.net/browse/HHH-5409
        // necessary to compare manually
        List<AnimalInFoodChainDTO> thisAnimals = this.getAnimalsInFoodChain();
        List<AnimalInFoodChainDTO> thatAnimals = that.getAnimalsInFoodChain();
        if (thisAnimals == thatAnimals) return true;

        if (thisAnimals != null && thatAnimals != null) {
            if (thisAnimals.size() != thatAnimals.size()) return false;

            for (int i = 0; i < thisAnimals.size(); i++) {
                if (thisAnimals.get(i).getIndexInFoodChain() != (thatAnimals.get(i)).getIndexInFoodChain() ||
                        !thisAnimals.get(i).getAnimal().equals(thatAnimals.get(i).getAnimal()))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnimalsInFoodChain());
    }
}
