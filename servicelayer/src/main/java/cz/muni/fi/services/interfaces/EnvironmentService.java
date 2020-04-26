package cz.muni.fi.services.interfaces;

import dao.entities.Environment;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * @author Katarina Matusova
 */
public interface EnvironmentService {
    /**
     * Creates new environment entity in database
     * @param environment instance of Environment to be created
     */
    void createEnvironment(Environment environment) throws DataAccessException;

    /**
     * Gets all Environments from the database
     * @return List of all Environment from the database
     */
    List<Environment> getAllEnvironments() throws DataAccessException;

    /**
     * Gets instance of environment by its id.
     * @param id id of desired environment
     * @return Environment
     */
    Environment getEnvironment(Long id) throws DataAccessException;

    /**
     * Updates given environment in the database.
     * @param environment instance of environment to be updated.
     */
    void updateEnvironment(Environment environment) throws DataAccessException;

    /**
     * Removes given environment from the database
     * @param id of Environment to be removed.
     */
    void deleteEnvironment(Long id) throws DataAccessException;
}
