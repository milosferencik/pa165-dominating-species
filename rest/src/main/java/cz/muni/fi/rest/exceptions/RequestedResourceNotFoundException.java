package cz.muni.fi.rest.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 *
 * @author Ondrej Slimak
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Requested resource was not found.")
public class RequestedResourceNotFoundException extends RuntimeException {
}
