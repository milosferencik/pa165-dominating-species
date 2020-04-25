package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalCreateDTO;
import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.dto.AnimalListDTO;

import java.util.List;

/**
 * @author Milos Ferencik 24/4/2020
 */
public interface AnimalFacade {
    /**
     * Create new animal
     * @param animalCreateDTO animal to create
     * @return Id of new Animal
     */
    Long createAnimal(AnimalCreateDTO animalCreateDTO);

    /**
     * Update animal data
     * @param animalDTO animal to be updated
     */
    void updateAnimal(AnimalDTO animalDTO);

    /**
     * Delete animal
     * @param id id of animal to be delete
     */
    void deleteAnimal(Long id);

    /**
     * Get all stored animals
     * @return Animals
     */
    List<AnimalListDTO> getAllAnimals();

    /**
     * Get stored animals with the environment
     * @param environmentId id of environment
     * @return Animals
     */
    List<AnimalListDTO> getAnimalsByEnvironment(Long environmentId);

    /**
     * Find animal with the id
     * @param id id of animal to be found
     * @return Animal
     */
    AnimalDTO getAnimalById(Long id);
}
