package dao.interfaces;

import dao.entities.Environment;

import java.util.List;

/**
 * Created by Kostka on 23/03/2020.
 */
public interface EnvironmentDao {
    public void createEnvironment(Environment environment);
    public List<Environment> getAllEnvironments();
    public Environment getEnvironment(Long id);
    public void updateEnvironment(Environment environment);
    public void deleteEnvironment(Environment environment);
}
