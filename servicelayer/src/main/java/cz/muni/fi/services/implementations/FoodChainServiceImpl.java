package cz.muni.fi.services.implementations;

import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.FoodChain;
import dao.interfaces.FoodChainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Milos Ferencik on 18/04/2020.
 */
@Service
public class FoodChainServiceImpl implements FoodChainService {
    @Autowired
    private FoodChainDao foodChainDao;

    @Override
    public void createFoodChain(FoodChain foodChain) throws DataAccessException {
        try {
            foodChainDao.createFoodChain(foodChain);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not create foodChain.", e);
        }
    }

    @Override
    public List<FoodChain> getAllFoodChains() throws DataAccessException {
        try {
            return foodChainDao.getAllFoodChains();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not get all foodChains.", e);
        }
    }

    @Override
    public FoodChain getFoodChain(Long id) throws DataAccessException {
        try {
            return foodChainDao.getFoodChain(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Error when finding foodChain.", e);
        }
    }

    @Override
    public void updateFoodChain(FoodChain foodChain) throws DataAccessException {
        try {
            foodChainDao.updateFoodChain(foodChain);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not update foodChain.", e);
        }
    }

    @Override
    public void deleteFoodChain(FoodChain foodChain) throws DataAccessException {
        try {
            foodChainDao.deleteFoodChain(foodChain);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not delete foodChain.", e);
        }
    }
}
