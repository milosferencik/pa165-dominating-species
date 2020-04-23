package cz.muni.fi.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodWebDTO {
    public Map<AnimalListDTO, List<AnimalListDTO>> foodWeb = new HashMap<>();

    public Map<AnimalListDTO, List<AnimalListDTO>> getFoodWeb() {
        return foodWeb;
    }

    public void setFoodWeb(Map<AnimalListDTO, List<AnimalListDTO>> foodWeb) {
        this.foodWeb = foodWeb;
    }


}
