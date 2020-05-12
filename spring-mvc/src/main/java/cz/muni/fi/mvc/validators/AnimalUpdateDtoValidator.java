package cz.muni.fi.mvc.validators;

import cz.muni.fi.dto.AnimalDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AnimalUpdateDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AnimalDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AnimalDTO animalDTO = (AnimalDTO) target;

        if (animalDTO.getEnvironment() == null) return;

        if (animalDTO.getName() == null || animalDTO.getName().isEmpty())
            errors.rejectValue("name", "animal.name.error");

        if (animalDTO.getSpecies() == null || animalDTO.getSpecies().isEmpty())
            errors.rejectValue("species", "animal.species.error");
    }
}
