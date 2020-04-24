package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalCreateDTO;
import cz.muni.fi.dto.AnimalDTO;

import java.util.List;

public interface AnimalFacade {
    Long createAnimal(AnimalCreateDTO animalCreateDTO);
    void updateAnimal(AnimalDTO animalDTO);
    void deleteAnimal(Long id);
    List<AnimalDTO> getAllAnimals();
    List<AnimalDTO> getAnimalsByEnvironment(Long environmentId);
    AnimalDTO getAnimalById(Long id);
}
