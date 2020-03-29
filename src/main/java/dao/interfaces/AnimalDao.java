package dao.interfaces;

import dao.entities.Animal;

import java.util.List;

/**
 * Created by Matusova on 26/03/2020.
 */
public interface AnimalDao {
    /**
     * Creates animal, by persisting animal into the database.
     * @param animal instance of animal
     */
    public void createAnimal(Animal animal);

    /**
     * Gets all animals from database.
     * @return List of all animals from database.
     */
    public List<Animal> getAllAnimals();

    /**
     * Gets an animal with given id from database.
     * @param id animal's id
     * @return animal from database
     */
    public Animal getAnimal(Long id);

    /**
     * Updates animal with new attributes.
     * @param animal already stored animal, which shall be updated
     */
    public void updateAnimal(Animal animal);

    /**
     * Finds given animal and deletes it from the database.
     * @param animal already stored animal, which shall be deleted
     */
    public void deleteAnimal(Animal animal);
}
