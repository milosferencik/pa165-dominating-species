package cz.muni.fi.dto;

public class AnimalInFoodChainDTO {

    private Long id;
    private int indexInFoodChain;
    private AnimalListDTO animal;

    public void setId(Long id) {
        this.id = id;
    }

    public void setIndexInFoodChain(int indexInFoodChain) {
        this.indexInFoodChain = indexInFoodChain;
    }

    public void setAnimal(AnimalListDTO animal) {
        this.animal = animal;
    }


    public Long getId() {
        return id;
    }

    public int getIndexInFoodChain() {
        return indexInFoodChain;
    }

    public AnimalListDTO getAnimal() {
        return animal;
    }

}
