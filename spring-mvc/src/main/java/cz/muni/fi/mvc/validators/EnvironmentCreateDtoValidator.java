package cz.muni.fi.mvc.validators;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EnvironmentCreateDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EnvironmentCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EnvironmentCreateDTO environmentCreateDTO = (EnvironmentCreateDTO) target;

        if (environmentCreateDTO.getName() == null || environmentCreateDTO.getName().isEmpty())
            errors.rejectValue("name", "nameNull");

        if (environmentCreateDTO.getDescription() == null || environmentCreateDTO.getDescription().isEmpty())
            errors.rejectValue("description", "descriptionNull");


    }

}
