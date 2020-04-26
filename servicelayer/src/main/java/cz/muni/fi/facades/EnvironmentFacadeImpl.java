package cz.muni.fi.facades;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.EnvironmentListDTO;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.EnvironmentService;
import dao.entities.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Kostka on 25/04/2020.
 */

@Service
@Transactional
public class EnvironmentFacadeImpl implements EnvironmentFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private EnvironmentService environmentService;

    @Override
    public Long createEnvironment(EnvironmentCreateDTO environmentCreateDTO) {
        Environment environment = beanMappingService.mapTo(environmentCreateDTO, Environment.class);
        environmentService.createEnvironment(environment);
        return environment.getId();
    }

    @Override
    public void updateEnvironment(EnvironmentDTO environmentDTO) {
        Environment environment = beanMappingService.mapTo(environmentDTO, Environment.class);
        environmentService.updateEnvironment(environment);
    }

    @Override
    public void deleteEnvironment(Long id) {
        environmentService.deleteEnvironment(id);
    }

    @Override
    public EnvironmentDTO getEnvironmentById(Long id) {
        Environment environment = environmentService.getEnvironment(id);
        return (environment == null) ? null : beanMappingService.mapTo(environment, EnvironmentDTO.class);
    }

    @Override
    public List<EnvironmentListDTO> getAllEnvironment() {
        return beanMappingService.mapTo(environmentService.getAllEnvironments(), EnvironmentListDTO.class);
    }
}
