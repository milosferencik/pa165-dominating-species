package dao.implementations;

import dao.entities.Animal;
import dao.entities.Environment;
import dao.entities.User;
import dao.interfaces.AnimalDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Matusova on 26/03/2020.
 */
public class AnimalImpl implements AnimalDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        entityManager.persist(animal);
    }

    public List<Animal> getAllAnimals() {
        return entityManager.createQuery("SELECT a FROM Animal a", Animal.class).getResultList();
    }

    public Animal getAnimal(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return entityManager.find(Animal.class, id);
    }

    public void updateAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        entityManager.merge(animal);
    }

    public void deleteAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        entityManager.remove(animal);
    }
}
