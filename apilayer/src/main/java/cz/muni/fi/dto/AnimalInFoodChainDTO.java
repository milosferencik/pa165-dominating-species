package cz.muni.fi.dto;

import java.util.Objects;

public class AnimalInFoodChainDTO {

    private Long id;
    private int indexInFoodChain;
    private AnimalDTO animal;
    private FoodChainDTO foodChain;

    public FoodChainDTO getFoodChain() {
        return foodChain;
    }

    public void setFoodChain(FoodChainDTO foodChain) {
        this.foodChain = foodChain;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIndexInFoodChain(int indexInFoodChain) {
        this.indexInFoodChain = indexInFoodChain;
    }

    public void setAnimal(AnimalDTO animal) {
        this.animal = animal;
    }


    public Long getId() {
        return id;
    }

    public int getIndexInFoodChain() {
        return indexInFoodChain;
    }

    public AnimalDTO getAnimal() {
        return animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalInFoodChainDTO)) return false;
        AnimalInFoodChainDTO that = (AnimalInFoodChainDTO) o;
        return getIndexInFoodChain() == that.getIndexInFoodChain() &&
                getFoodChain().getId().equals(that.getFoodChain().getId()) &&
                getAnimal().equals(that.getAnimal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFoodChain().getId(), getAnimal(), getIndexInFoodChain());
    }
}
