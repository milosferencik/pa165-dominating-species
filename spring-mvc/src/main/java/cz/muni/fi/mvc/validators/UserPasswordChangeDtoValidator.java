package cz.muni.fi.mvc.validators;

import cz.muni.fi.dto.UserPasswordChangeDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Katarina Matusova
 */
public class UserPasswordChangeDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserPasswordChangeDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserPasswordChangeDTO userDTO = (UserPasswordChangeDTO) target;
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()){
            errors.rejectValue("password", "user.password.old");
        }
        if (userDTO.getNewPassword() == null || userDTO.getNewPassword().trim().isEmpty()){
            errors.rejectValue("newPassword", "user.password.new");
        }
        if (userDTO.getRepeatedPassword() == null || userDTO.getRepeatedPassword().trim().isEmpty()){
            errors.rejectValue("repeatedPassword", "user.password.repeated");
        }
    }
}
