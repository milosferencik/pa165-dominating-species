package cz.muni.fi.pa165.dominatingspecies.mvc.validators;

import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Milos Ferencik
 */
public class EnvironmentUpdateDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return EnvironmentDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EnvironmentDTO environmentDTO = (EnvironmentDTO) target;

        if (environmentDTO.getId() == null) return;

        if (environmentDTO.getName() == null || environmentDTO.getName().isEmpty())
            errors.rejectValue("name", "environment.name.error");

        if (environmentDTO.getDescription() == null || environmentDTO.getDescription().isEmpty())
            errors.rejectValue("description", "environment.description.error");
    }
}
