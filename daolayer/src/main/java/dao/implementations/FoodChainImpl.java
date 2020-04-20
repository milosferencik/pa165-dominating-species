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

    public FoodChain createFoodChain(FoodChain foodChain) {
        entityManager.persist(foodChain);
        return foodChain;
    }

    public List<FoodChain> getAllFoodChains() {
        return entityManager.createQuery("SELECT fd FROM FoodChain fd", FoodChain.class).getResultList();
    }

    public FoodChain getFoodChain(Long id) {
        return entityManager.find(FoodChain.class, id);
    }

    public FoodChain updateFoodChain(FoodChain foodChain) {
        return entityManager.merge(foodChain);

    }

    public FoodChain deleteFoodChain(FoodChain foodChain) {
        entityManager.remove(foodChain);
        return foodChain;
    }

    @Override
    public List<FoodChain> getFoodChainsWithAnimal(Animal animal) {
        return entityManager.createQuery("SELECT fd FROM FoodChain fd INNER JOIN fd.animalsInFoodChain fda WHERE fda.animal = :animal", FoodChain.class)
                .setParameter("animal", animal)
                .getResultList();
    }
}
