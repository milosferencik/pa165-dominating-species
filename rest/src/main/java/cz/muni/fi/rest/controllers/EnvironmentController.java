package cz.muni.fi.rest.controllers;

import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.EnvironmentListDTO;
import cz.muni.fi.facades.EnvironmentFacade;
import cz.muni.fi.rest.exceptions.RequestedResourceNotFoundException;
import org.dozer.inject.Inject;
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

    @Inject
    private EnvironmentFacade environmentFacade;  // TODO: not able to inject

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



}
