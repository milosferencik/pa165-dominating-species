package cz.muni.fi.pa165.dominatingspecies.dao.implementations;

import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
import cz.muni.fi.pa165.dominatingspecies.dao.interfaces.EnvironmentDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Kostka on 23/03/2020.
 */
@Repository
public class EnvironmentImpl implements EnvironmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createEnvironment(Environment environment) {
        entityManager.persist(environment);
    }

    public List<Environment> getAllEnvironments() {
        return entityManager.createQuery("SELECT e FROM Environment e", Environment.class).getResultList();
    }

    public Environment getEnvironment(Long id) {
        return entityManager.find(Environment.class, id);
    }

    public void updateEnvironment(Environment environment) {
        entityManager.merge(environment);
    }

    public void deleteEnvironment(Long id) {
        Environment env = getEnvironment(id);
        if (env != null) {
            entityManager.remove(env);
        }
    }
}
