package cz.muni.fi.services.interfaces;

import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import dao.entities.Animal;
import dao.entities.Environment;

import java.util.List;

public interface AnimalService {
    /**
     * Create new Animal
     * @param animal animal to create
     *  @throws ServiceDataAccessException when db access fails
     */
     void create(Animal animal) throws ServiceDataAccessException;

    /**
     * Find animal by id
     * @param id id of animal to be found
     * @return animal with given id or null
     * @throws ServiceDataAccessException when db access fails
     */
     Animal findById(long id) throws ServiceDataAccessException;

    /**
     * Find all stored animals
     * @return all animals in database
     * @throws ServiceDataAccessException when db access fails
     */
     List<Animal> findAll() throws ServiceDataAccessException;

    /**
     * Find all animals in given environment
     * @param environment environment of which to find animals
     * @return animals of given environment
     * @throws ServiceDataAccessException when db access fails
     */
    List<Animal> findAnimalsByEnvironment(Environment environment) throws ServiceDataAccessException;

    /**
     * Update animal data
     * @param animal animal to be updated
     * @throws ServiceDataAccessException when db access fails
     */
     void update(Animal animal) throws ServiceDataAccessException;

    /**
     * Remove animal
     * @param animal to be removed
     * @throws ServiceDataAccessException when db access fails
     */
     void remove(Animal animal) throws ServiceDataAccessException;
}
