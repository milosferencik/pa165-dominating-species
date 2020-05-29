package cz.muni.fi.pa165.dominatingspecies.service.services.implementations;

import cz.muni.fi.pa165.dominatingspecies.service.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.EnvironmentService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
import cz.muni.fi.pa165.dominatingspecies.dao.interfaces.EnvironmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Katarina Matusova
 */
@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    @Autowired
    private EnvironmentDao environmentDao;

    @Override
    public void createEnvironment(Environment environment) throws DataAccessException {
        try {
            environmentDao.createEnvironment(environment);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not create environment.", ex);
        }
    }

    @Override
    public List<Environment> getAllEnvironments() throws DataAccessException {
        try {
            return environmentDao.getAllEnvironments();
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not get all environments.", ex);
        }
    }

    @Override
    public Environment getEnvironment(Long id) throws DataAccessException {
        try {
            return environmentDao.getEnvironment(id);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not get all environments.", ex);
        }
    }

    @Override
    public void updateEnvironment(Environment environment) throws DataAccessException {
        try {
            environmentDao.updateEnvironment(environment);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not update environment.", ex);
        }
    }

    @Override
    public void deleteEnvironment(Long id) throws DataAccessException {
        try {
            environmentDao.deleteEnvironment(id);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not delete environment.", ex);
        }
    }
}