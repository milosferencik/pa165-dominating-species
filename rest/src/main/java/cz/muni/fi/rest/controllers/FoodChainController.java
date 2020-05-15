package cz.muni.fi.rest.controllers;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.EnvironmentListDTO;
import cz.muni.fi.dto.FoodChainDTO;
import cz.muni.fi.facades.FoodChainFacade;
import cz.muni.fi.rest.exceptions.RequestedResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Kostka on 15/05/2020.
 */
@RestController
@RequestMapping("/foodChains")
public class FoodChainController {

    @Autowired
    private FoodChainFacade foodChainFacade;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FoodChainDTO> getFoodChains() {
        return foodChainFacade.getAllFoodChains();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final FoodChainDTO getFoodChain(@PathVariable("id") long id) {
        FoodChainDTO foodChainDTO = foodChainFacade.getFoodChainById(id);
        if (foodChainDTO == null) {
            throw new RequestedResourceNotFoundException();
        }
        return foodChainDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteFoodChain(@PathVariable("id") long id) {
        try {
            foodChainFacade.deleteFoodChain(id);
        } catch (Exception ex) {
            throw new RequestedResourceNotFoundException();
        }
    }

}