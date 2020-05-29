package cz.muni.fi.pa165.dominatingspecies.mvc.validators;

import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Ondrej Slimak
 */

public class EnvironmentCreateDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EnvironmentCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EnvironmentCreateDTO environmentCreateDTO = (EnvironmentCreateDTO) target;

        if (environmentCreateDTO.getName() == null || environmentCreateDTO.getName().isEmpty())
            errors.rejectValue("name", "environment.name.error");

        if (environmentCreateDTO.getDescription() == null || environmentCreateDTO.getDescription().isEmpty())
            errors.rejectValue("description", "environment.description.error");
    }
}