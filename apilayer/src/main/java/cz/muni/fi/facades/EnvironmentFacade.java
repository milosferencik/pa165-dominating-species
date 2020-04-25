package cz.muni.fi.facades;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;

/**
 * Created by Kostka on 25/04/2020.
 */
public interface EnvironmentFacade {
    Long createAnimal(EnvironmentCreateDTO environmentCreateDTO);
    void updateAnimal(EnvironmentDTO environmentDTO);
    void deleteAnimal(Long id);
    EnvironmentDTO getAnimalById(Long id);
}
