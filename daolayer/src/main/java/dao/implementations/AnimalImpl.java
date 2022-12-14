package dao.implementations;

import dao.entities.Animal;
import dao.entities.Environment;
import dao.entities.User;
import dao.interfaces.AnimalDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Matusova on 26/03/2020.
 */

@Repository
public class AnimalImpl implements AnimalDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createAnimal(Animal animal) {
        entityManager.persist(animal);
    }

    public List<Animal> getAllAnimals() {
        return entityManager.createQuery("SELECT a FROM Animal a", Animal.class).getResultList();
    }

    public Animal getAnimal(Long id) {

        return entityManager.find(Animal.class, id);
    }

    public void updateAnimal(Animal animal) {
        entityManager.merge(animal);
    }

    public void deleteAnimal(Animal animal) {
        entityManager.remove(animal);
    }
}
