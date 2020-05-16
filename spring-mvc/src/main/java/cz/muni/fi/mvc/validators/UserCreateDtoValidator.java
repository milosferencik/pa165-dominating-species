package cz.muni.fi.mvc.validators;

import cz.muni.fi.dto.UserCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Katarina Matusova
 */
public class UserCreateDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateDTO userCreateDTO = (UserCreateDTO) target;

        if (userCreateDTO.getSurname() == null || userCreateDTO.getSurname().trim().isEmpty()) {
            errors.rejectValue("surname", "user.surname.error");
        }

        if (userCreateDTO.getName() == null || userCreateDTO.getName().trim().isEmpty()) {
            errors.rejectValue("name", "user.name.error");
        }

        if (userCreateDTO.getPassword() == null || userCreateDTO.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", "user.password.error");
        }

        if (userCreateDTO.getEmail() == null || userCreateDTO.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "user.email.error");
        }

        if (!userCreateDTO.getEmail().trim().isEmpty() && !userCreateDTO.getEmail().matches(".+@.+\\..{2,3}")) {
            errors.rejectValue("email", "user.email.format.error");
        }
    }
}
