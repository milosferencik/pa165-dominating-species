package dao.implementations;

import dao.entities.Environment;
import dao.interfaces.EnvironmentDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Kostka on 23/03/2020.
 */
@Repository
public class EnvironmentImpl implements EnvironmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createEnvironment(Environment environment) {
        if (environment == null) {
            throw new IllegalArgumentException("Environment cannot be null");
        }
        entityManager.persist(environment);
    }

    public List<Environment> getAllEnvironments() {
        return entityManager.createQuery("SELECT e FROM Environment e", Environment.class).getResultList();
    }

    public Environment getEnvironment(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        return entityManager.find(Environment.class, id);
    }

    public void updateEnvironment(Environment environment) {
        if (environment == null) {
            throw new IllegalArgumentException("Environment cannot be null");
        }
        if (environment.getName() == null || environment.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (environment.getDescription() == null || environment.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (environment.getId() == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        entityManager.merge(environment);
    }

    public void deleteEnvironment(Environment environment) {
        if (environment == null) {
            throw new IllegalArgumentException("Environment cannot be null");
        }
        entityManager.remove(environment);
    }
}
