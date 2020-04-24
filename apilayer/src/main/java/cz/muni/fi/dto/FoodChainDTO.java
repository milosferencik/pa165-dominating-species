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
}
