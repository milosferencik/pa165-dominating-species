package cz.muni.fi.pa165.dominatingspecies.service.services.exceptions;

import org.springframework.dao.DataAccessException;

public class ServiceDataAccessException extends DataAccessException {

    public ServiceDataAccessException(String msg) {
        super(msg);
    }

    public ServiceDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
