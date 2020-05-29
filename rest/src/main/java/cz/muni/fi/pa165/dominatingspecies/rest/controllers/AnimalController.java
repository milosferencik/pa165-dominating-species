package cz.muni.fi.pa165.dominatingspecies.rest.controllers;



import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalListDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalUpdateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.facades.AnimalFacade;
import cz.muni.fi.pa165.dominatingspecies.rest.exceptions.RequestedResourceNotFoundException;
import cz.muni.fi.pa165.dominatingspecies.rest.exceptions.ResourceAlreadyExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ondrej Slimak
 */

@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalFacade animalFacade;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AnimalListDTO> getAnimals() {
        return animalFacade.getAllAnimals();
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AnimalDTO getAnimal(@PathVariable("id") long id) {
        AnimalDTO animal = animalFacade.getAnimalById(id);
        if (animal == null) {
            throw new RequestedResourceNotFoundException();
        }
        return animal;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/environment/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AnimalListDTO> getAnimalByEnvironment(@PathVariable("id") long id) {
        List<AnimalListDTO> animals = animalFacade.getAnimalsByEnvironment(id);
        if (animals == null) {
            throw new RequestedResourceNotFoundException();
        }
        return animals;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteAnimal(@PathVariable("id") long id) {
        try {
            animalFacade.deleteAnimal(id);
        } catch (Exception ex) {
            throw new RequestedResourceNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AnimalCreateDTO createAnimal(@RequestBody AnimalCreateDTO animal) {
        try {
            animalFacade.createAnimal(animal);
            return animal;
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AnimalDTO updateAnimal(@PathVariable("id") Long id, @RequestBody AnimalUpdateDTO animal) {
        animal.setId(id);;
        try {
            animalFacade.updateAnimal(animal);
            return animalFacade.getAnimalById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }
}