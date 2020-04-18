package cz.muni.fi.services.interfaces;

import dao.entities.Environment;

import java.util.List;

/**
 * @author Katarina Matusova
 */
public interface EnvironmentService {
    /**
     * Creates new environment entity in database
     * @param environment instance of Environment to be created
     */
    public void createEnvironment(Environment environment);

    /**
     * Gets all Environments from the database
     * @return List of all Environment from the database
     */
    public List<Environment> getAllEnvironments();

    /**
     * Gets instance of environment by its id.
     * @param id id of desired environment
     * @return Environment
     */
    public Environment getEnvironment(Long id);

    /**
     * Updates given environment in the database.
     * @param environment instance of environment to be updated.
     */
    public void updateEnvironment(Environment environment);

    /**
     * Removes given environment from the database
     * @param environment instance of Environment to be removed.
     */
    public void deleteEnvironment(Environment environment);
}
