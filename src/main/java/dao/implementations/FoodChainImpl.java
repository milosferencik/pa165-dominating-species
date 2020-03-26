package dao.implementations;

import dao.entities.FoodChain;
import dao.interfaces.FoodChainDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ondrej Slimak on 25/03/2020.
 */
public class FoodChainImpl implements FoodChainDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createFoodChain(FoodChain foodChain) {
        if (foodChain == null)
            throw new IllegalArgumentException("FoodChain cannot be null");

        if (foodChain.getAnimals() == null)
            throw new IllegalArgumentException("FoodChain animals cannot be null");

        if (foodChain.getAnimals().isEmpty())
            throw new IllegalArgumentException("FoodChain cannot be empty");

        entityManager.persist(foodChain);
    }

    public List<FoodChain> getAllFoodChains() {

        return entityManager.createQuery("SELECT fd FROM FoodChain fd", FoodChain.class).getResultList();
    }

    public FoodChain getFoodChain(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");
        return entityManager.find(FoodChain.class, id);
    }

    public void updateFoodChain(FoodChain foodChain) {
        if (foodChain == null)
            throw new IllegalArgumentException("FoodChain cannot be null");

        if (foodChain.getAnimals() == null)
            throw new IllegalArgumentException("FoodChain animals cannot be null");

        if (foodChain.getAnimals().isEmpty())
            throw new IllegalArgumentException("FoodChain cannot be empty");

        entityManager.merge(foodChain);

    }

    public void deleteFoodChain(FoodChain foodChain) {
        if (foodChain == null)
            throw new IllegalArgumentException("FoodChain cannot be null");

        entityManager.merge(foodChain);
    }
}
