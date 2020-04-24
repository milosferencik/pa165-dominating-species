package cz.muni.fi.dto;

import java.util.List;

public class FoodChainCreateDTO {

    private List<AnimalInFoodChainDTO> animalsInFoodChain;

    public List<AnimalInFoodChainDTO> getAnimalsInFoodChain() {
        return animalsInFoodChain;
    }

    public void setAnimalsInFoodChain(List<AnimalInFoodChainDTO> animalsInFoodChain) {
        this.animalsInFoodChain = animalsInFoodChain;
    }
}
