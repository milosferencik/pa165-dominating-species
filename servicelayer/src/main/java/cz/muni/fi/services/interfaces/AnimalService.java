package cz.muni.fi.services.interfaces;

import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import dao.entities.Animal;
import dao.entities.Environment;

import java.util.List;

/**
 * @author  Ondrej Slimak on 18/04/2020.
 */
public interface AnimalService {
    /**
     * Create new Animal
     * @param animal animal to create
     *  @throws ServiceDataAccessException when db access fails
     */
     void createAnimal(Animal animal) throws ServiceDataAccessException;

    /**
     * Find animal by id
     * @param id id of animal to be found
     * @return animal with given id or null
     * @throws ServiceDataAccessException when db access fails
     */
     Animal getAnimal(long id) throws ServiceDataAccessException;

    /**
     * Find all stored animals
     * @return all animals in database
     * @throws ServiceDataAccessException when db access fails
     */
     List<Animal> getAllAnimals() throws ServiceDataAccessException;

    /**
     * Find all animals in given environment
     * @param environment environment of which to find animals
     * @return animals of given environment
     * @throws ServiceDataAccessException when db access fails
     */
    List<Animal> getAnimalsByEnvironment(Environment environment) throws ServiceDataAccessException;

    /**
     * Update animal data
     * @param animal animal to be updated
     * @throws ServiceDataAccessException when db access fails
     */
     void updateAnimal(Animal animal) throws ServiceDataAccessException;

    /**
     * Remove animal
     * @param animal to be removed
     * @throws ServiceDataAccessException when db access fails
     */
     void deleteAnimal(Animal animal) throws ServiceDataAccessException;
}
