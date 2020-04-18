package cz.muni.fi.services.implementations;

import cz.muni.fi.services.interfaces.EnvironmentService;
import dao.entities.Environment;
import dao.interfaces.EnvironmentDao;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createEnvironment(Environment environment) {
            environmentDao.createEnvironment(environment);
    }

    @Override
    public List<Environment> getAllEnvironments() {
        return environmentDao.getAllEnvironments();
    }

    @Override
    public Environment getEnvironment(Long id) {
        return environmentDao.getEnvironment(id);
    }

    @Override
    public void updateEnvironment(Environment environment) {
        environmentDao.updateEnvironment(environment);
    }

    @Override
    public void deleteEnvironment(Environment environment) {
        environmentDao.deleteEnvironment(environment);
    }
}