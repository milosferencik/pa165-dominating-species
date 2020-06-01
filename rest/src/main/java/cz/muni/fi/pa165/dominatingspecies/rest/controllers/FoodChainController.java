package cz.muni.fi.pa165.dominatingspecies.rest.controllers;


import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainDTO;
import cz.muni.fi.pa165.dominatingspecies.api.facades.FoodChainFacade;
import cz.muni.fi.pa165.dominatingspecies.rest.exceptions.RequestedResourceNotFoundException;
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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final FoodChainCreateDTO createFoodChain(@RequestBody FoodChainCreateDTO foodChainCreateDTO) {
        try {
            foodChainFacade.createFoodChain(foodChainCreateDTO);
            return foodChainCreateDTO;
        } catch (Exception ex) {
            throw new DataAccessResourceFailureException("Failed to create new FoodChain");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/animal/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<FoodChainDTO> getFoodChainsByAnimal(@PathVariable("id") long id) {
        List<FoodChainDTO> foodChainDTOS = foodChainFacade.getFoodChainsWithAnimal(id);
        if (foodChainDTOS == null) {
            throw new RequestedResourceNotFoundException();
        }
        return foodChainDTOS;
    }

}
