package cz.muni.fi.dto;

/**
 * @author Ondrej Slimak
 */
public class AnimalInFoodChainCreateDTO {
    private Long id;
    private int indexInFoodChain;
    private Long animalId;
    private FoodChainCreateDTO foodChain;

    public FoodChainCreateDTO getFoodChain() {
        return foodChain;
    }

    public void setFoodChain(FoodChainCreateDTO foodChain) {
        this.foodChain = foodChain;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIndexInFoodChain(int indexInFoodChain) {
        this.indexInFoodChain = indexInFoodChain;
    }

    public void setAnimalId(Long animal) {
        this.animalId = animal;
    }


    public Long getId() {
        return id;
    }

    public int getIndexInFoodChain() {
        return indexInFoodChain;
    }

    public Long getAnimalId() {
        return animalId;
    }
}
