package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalCreateDTO;
import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.dto.AnimalListDTO;
import cz.muni.fi.dto.AnimalUpdateDTO;

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
     * @param animalUpdateDto animal to be updated
     */
    void updateAnimal(AnimalUpdateDTO animalUpdateDto);

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
     * @return AnimalDTO
     */
    AnimalDTO getAnimalById(Long id);

    /**
     * Find animal with the id
     * @param id id of animal to be found
     * @return AnimalUpdateDTO
     */
    AnimalUpdateDTO getAnimalUpdateById(Long id);
}
