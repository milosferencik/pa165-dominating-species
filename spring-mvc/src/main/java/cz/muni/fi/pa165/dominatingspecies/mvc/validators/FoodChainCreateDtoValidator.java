package cz.muni.fi.pa165.dominatingspecies.mvc.validators;

import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Kostka on 16/05/2020.
 */
public class FoodChainCreateDtoValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return FoodChainCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //FoodChainCreateDTO foodChainCreateDTO = (FoodChainCreateDTO) target;

        //if (foodChainCreateDTO.getAnimalsInFoodChain() == null || foodChainCreateDTO.getAnimalsInFoodChain().isEmpty())
          //  errors.rejectValue("animals", "foodChain.animals.error");
    }
}
