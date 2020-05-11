package cz.muni.fi.mvc.validators;

import cz.muni.fi.dto.AnimalCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AnimalCreateDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AnimalCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AnimalCreateDTO animalCreateDTO = (AnimalCreateDTO) target;

        if (animalCreateDTO.getName() == null || animalCreateDTO.getName().isEmpty())
            errors.rejectValue("name", "nameNull");

        if (animalCreateDTO.getSpecies() == null || animalCreateDTO.getSpecies().isEmpty())
            errors.rejectValue("species", "speciesNull");

        if (animalCreateDTO.getEnvironmentId() == null)
            errors.rejectValue("environmentId", "environmentIdNull");
    }
}
