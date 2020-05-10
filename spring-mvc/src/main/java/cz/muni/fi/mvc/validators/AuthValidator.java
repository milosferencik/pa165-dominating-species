package cz.muni.fi.mvc.validators;

import cz.muni.fi.dto.AuthenticateUserDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AuthValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AuthenticateUserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthenticateUserDTO authUserDto = (AuthenticateUserDTO) target;

        if (authUserDto.getEmail() == null || authUserDto.getEmail().isEmpty())
            errors.rejectValue("email", "mailNull");

        if (authUserDto.getPassword() == null)
            errors.rejectValue("password", "passwordNull");

        if (authUserDto.getEmail() != null && !authUserDto.getEmail().matches(".+@.+\\..+"))
            errors.rejectValue("email", "wrongFormat");
    }
}
