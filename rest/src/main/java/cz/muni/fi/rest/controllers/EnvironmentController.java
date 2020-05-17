package cz.muni.fi.rest.controllers;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.EnvironmentListDTO;
import cz.muni.fi.facades.EnvironmentFacade;
import cz.muni.fi.rest.exceptions.RequestedResourceNotFoundException;
import cz.muni.fi.rest.exceptions.ResourceAlreadyExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 * @author Ondrej Slimak
 */
@RestController
@RequestMapping("/environments")
public class EnvironmentController {

    @Autowired
    private EnvironmentFacade environmentFacade;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EnvironmentListDTO> getEnvironments() {
        return environmentFacade.getAllEnvironment();
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final EnvironmentDTO getEnvironment(@PathVariable("id") long id) {
        EnvironmentDTO env = environmentFacade.getEnvironmentById(id);
        if (env == null) {
            throw new RequestedResourceNotFoundException();
        }
        return env;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteEnvironment(@PathVariable("id") long id) {
        try {
            environmentFacade.deleteEnvironment(id);
        } catch (Exception ex) {
            throw new RequestedResourceNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EnvironmentCreateDTO createEnvironment(@RequestBody EnvironmentCreateDTO env) {
        try {
            environmentFacade.createEnvironment(env);
            return env;
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EnvironmentDTO updateEnvironment(@PathVariable("id") Long id, @RequestBody EnvironmentDTO env) {
        env.setId(id);;

        try {
            environmentFacade.updateEnvironment(env);
            return environmentFacade.getEnvironmentById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

}
