package dao.interfaces;

import dao.entities.Animal;
import dao.entities.Environment;

import java.util.List;

/**
 * @author Matusova on 26/03/2020.
 */
public interface AnimalDao {
    /**
     * Creates animal, by persisting animal into the database.
     * @param animal instance of animal
     */
    void createAnimal(Animal animal);

    /**
     * Gets all animals from database.
     * @return List of all animals from database.
     */
    List<Animal> getAllAnimals();

    List<Animal> getAllAnimalsInEnvironment(Environment environment);

    /**
     * Gets an animal with given id from database.
     * @param id animal's id
     * @return animal from database
     */
    Animal getAnimal(Long id);

    /**
     * Updates animal with new attributes.
     * @param animal already stored animal, which shall be updated
     */
    void updateAnimal(Animal animal);

    /**
     * Finds given animal and deletes it from the database.
     * @param id of already stored animal, which shall be deleted
     */
    void deleteAnimal(Long id);
}
