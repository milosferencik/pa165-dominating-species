package cz.muni.fi.mvc.validators;

import cz.muni.fi.dto.FoodChainDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Kostka on 16/05/2020.
 */
public class FoodChainUpdateDtoValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return FoodChainDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FoodChainDTO foodChainDTO = (FoodChainDTO) target;

        if (foodChainDTO.getId() == null) return;

        if (foodChainDTO.getAnimalsInFoodChain() == null || foodChainDTO.getAnimalsInFoodChain().isEmpty())
            return;
    }
}
