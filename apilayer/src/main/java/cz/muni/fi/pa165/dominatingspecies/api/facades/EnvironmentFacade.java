package cz.muni.fi.pa165.dominatingspecies.api.facades;


import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentListDTO;

import java.util.List;

/**
 * @author Kostka on 25/04/2020.
 */
public interface EnvironmentFacade {
    /**
     * Create new environment
     * @param environmentCreateDTO
     * @return
     */
    Long createEnvironment(EnvironmentCreateDTO environmentCreateDTO);

    /**
     * Update environment
     * @param environmentDTO
     */
    void updateEnvironment(EnvironmentDTO environmentDTO);

    /**
     * Delete environment
     * @param id id of environment to be delete
     */
    void deleteEnvironment(Long id);

    /**
     * Find environment with the id
     * @param id id of environment to be found
     * @return
     */
    EnvironmentDTO getEnvironmentById(Long id);

    /**
     * Get all stored environment
     * @return
     */
    List<EnvironmentListDTO> getAllEnvironment();
}
