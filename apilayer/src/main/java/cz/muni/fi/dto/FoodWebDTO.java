package cz.muni.fi.dto;

import java.util.*;

public class FoodWebDTO {
    public Map<AnimalListDTO, List<AnimalListDTO>> foodWeb = new HashMap<>();

    public Map<AnimalListDTO, List<AnimalListDTO>> getFoodWeb() {
        return foodWeb;
    }

    public void setFoodWeb(Map<AnimalListDTO, List<AnimalListDTO>> foodWeb) {
        this.foodWeb = foodWeb;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodWebDTO)) return false;
        FoodWebDTO that = (FoodWebDTO) o;
        return getFoodWeb().equals(that.getFoodWeb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFoodWeb());
    }
}
