package dao.implementations;

import dao.entities.Animal;
import dao.entities.FoodChain;
import dao.interfaces.FoodChainDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ondrej Slimak on 25/03/2020.
 */
@Repository
public class FoodChainImpl implements FoodChainDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createFoodChain(FoodChain foodChain) {
        entityManager.persist(foodChain);
    }

    public List<FoodChain> getAllFoodChains() {
        return entityManager.createQuery("SELECT fd FROM FoodChain fd", FoodChain.class).getResultList();
    }

    public FoodChain getFoodChain(Long id) {
        return entityManager.find(FoodChain.class, id);
    }

    public void updateFoodChain(FoodChain foodChain) {
        entityManager.merge(foodChain);

    }

    public void deleteFoodChain(FoodChain foodChain) {
        entityManager.remove(foodChain);
    }

    @Override
    public List<FoodChain> getFoodChainsWithAnimal(Animal animal) {
        return entityManager.createQuery("SELECT fd FROM FoodChain fd INNER JOIN fd.Animals fda WHERE fda = :animal", FoodChain.class)
                .setParameter("animal", animal)
                .getResultList();
    }
}
