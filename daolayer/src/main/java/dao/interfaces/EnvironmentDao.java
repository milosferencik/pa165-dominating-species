package dao.interfaces;

import dao.entities.Environment;

import java.util.List;

/**
 * @author by Kostka on 23/03/2020.
 */
public interface EnvironmentDao {
    /**
     * Will create new environment in persistence layer.
     * @param environment instance of environment, that will be put into persistance layer.
     */
    void createEnvironment(Environment environment);

    /**
     * Will return all Environment from persistence layer.
     * @return List of all Environment from persistence layer
     */
    List<Environment> getAllEnvironments();

    /**
     * Will return instance of environment found by its id.
     * @param id id of wanted environment
     * @return Environment instance
     */
    Environment getEnvironment(Long id);

    /**
     * Will update given environment in persistence layer.
     * @param environment instance of environment, that will be updated in persistence layer.
     */
    void updateEnvironment(Environment environment);

    /**
     * Will remove given environment from persistaece layer.
     * @param id of environment, that will be removed from persistence layer.
     */
    void deleteEnvironment(Long id);
}
