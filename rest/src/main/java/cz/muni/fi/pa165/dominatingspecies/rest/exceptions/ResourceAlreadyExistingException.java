package cz.muni.fi.pa165.dominatingspecies.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason="The resource with name already exists")
public class ResourceAlreadyExistingException extends RuntimeException {
}
