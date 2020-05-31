package cz.muni.fi.pa165.dominatingspecies.mvc.validators;

import cz.muni.fi.pa165.dominatingspecies.api.dto.UserUpdateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Katarina Matusova
 */
public class UserUpdateDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserUpdateDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateDTO userDTO = (UserUpdateDTO) target;
        if (userDTO.getSurname() == null || userDTO.getSurname().trim().isEmpty()){
            errors.rejectValue("surname", "user.surname.error");
        }
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()){
            errors.rejectValue("name", "user.name.error");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()){
            errors.rejectValue("email", "user.email.error");
        } else if (!userDTO.getEmail().trim().isEmpty() && !userDTO.getEmail().matches(".+@.+\\..{2,3}")){
            errors.rejectValue("email", "user.email.format.error");
        }
    }
}
