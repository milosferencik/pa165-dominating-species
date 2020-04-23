package cz.muni.fi.dto;

import java.util.ArrayList;
import java.util.List;
import dao.entities.AnimalInFoodChain;

/**
 * @author Katarina Matusova
 */
public class FoodChainDTO {

    private Long id;
    private List<AnimalInFoodChain> animalsInFoodChain = new ArrayList<>();

    public FoodChainDTO() {
    }

    public Long getId() {
        return id;
    }

    public List<AnimalInFoodChain> getAnimalsInFoodChain() {
        return animalsInFoodChain;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnimalsInFoodChain(List<AnimalInFoodChain> animalsInFoodChain) {
        this.animalsInFoodChain = animalsInFoodChain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodChainDTO)) return false;
        FoodChainDTO that = (FoodChainDTO) o;

        // Hibernate BUG in PersistentBag: https://hibernate.atlassian.net/browse/HHH-5409
        // necessary to compare manually
        List<AnimalInFoodChain> thisAnimals = this.getAnimalsInFoodChain();
        List<AnimalInFoodChain> thatAnimals = that.getAnimalsInFoodChain();
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((animalsInFoodChain == null) ? 0 : animalsInFoodChain.hashCode());
        return result;
    }
}
