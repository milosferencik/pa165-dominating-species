package cz.muni.fi.facades;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.EnvironmentListDTO;

import java.util.List;

/**
 * Created by Kostka on 25/04/2020.
 */
public interface EnvironmentFacade {
    Long createEnvironment(EnvironmentCreateDTO environmentCreateDTO);
    void updateEnvironment(EnvironmentDTO environmentDTO);
    void deleteEnvironment(Long id);
    EnvironmentDTO getEnvironmentById(Long id);
    List<EnvironmentListDTO> getAllEnvironment();
}
