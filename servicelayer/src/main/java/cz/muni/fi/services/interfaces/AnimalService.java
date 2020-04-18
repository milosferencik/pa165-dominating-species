package cz.muni.fi.services.interfaces;

import dao.entities.Animal;

import java.util.List;

public interface AnimalService {
    /**
     * Create new Animal
     * @param animal animal to create
     */
     void create(Animal animal);

    /**
     * Find animal by id
     * @param id id of animal to be found
     * @return animal with given id or null
     */
     Animal findById(long id);

    /**
     * Find all stored animals
     * @return all animals in database
     */
     List<Animal> findAll();

    /**
     * Update animal data
     * @param animal animal to be updated
     */
     void update(Animal animal);

    /**
     * Remove animal
     * @param animal to be removed
     */
     void remove(Animal animal);
}
