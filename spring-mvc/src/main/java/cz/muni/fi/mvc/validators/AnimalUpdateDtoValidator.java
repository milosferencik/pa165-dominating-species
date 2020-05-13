package cz.muni.fi.mvc.validators;

import cz.muni.fi.dto.AnimalUpdateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AnimalUpdateDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AnimalUpdateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AnimalUpdateDTO animalUpdateDTO = (AnimalUpdateDTO) target;

        if (animalUpdateDTO.getId() == null) return;
        if (animalUpdateDTO.getEnvironmentId() == null) return;

        if (animalUpdateDTO.getName() == null || animalUpdateDTO.getName().isEmpty())
            errors.rejectValue("name", "animal.name.error");

        if (animalUpdateDTO.getSpecies() == null || animalUpdateDTO.getSpecies().isEmpty())
            errors.rejectValue("species", "animal.species.error");
    }
}
