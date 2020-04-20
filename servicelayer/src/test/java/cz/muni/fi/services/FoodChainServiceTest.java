package cz.muni.fi.services;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.services.interfaces.FoodChainService;
import dao.entities.FoodChain;
import dao.interfaces.FoodChainDao;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class FoodChainServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private FoodChainDao foodChainDao;

    @Autowired
    @InjectMocks
    private FoodChainService foodChainService;

    private Long counter = 10L;
    private Map<Long, FoodChain> foodChains = new HashMap<>();

    @BeforeClass
    public void beforeClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(foodChainDao.createFoodChain(any(FoodChain.class))).then(invoke -> {
                    FoodChain mockedFoodChain  = invoke.getArgumentAt(0, FoodChain.class);

                    if (mockedFoodChain == null) {
                        throw new IllegalArgumentException("FoodChain cannot be null");
                    }
                     //TODO: more checks/exceptions
                    long index = counter;
                    counter++;
                    mockedFoodChain.setId(index);
                    foodChains.put(index, mockedFoodChain);
                    return mockedFoodChain;
                });

        when(foodChainDao.updateFoodChain(any(FoodChain.class))).then(invoke -> {
            FoodChain mockedFoodChain  = invoke.getArgumentAt(0, FoodChain.class);
            //TODO: more checks/exceptions
            if (mockedFoodChain == null) {
                throw new IllegalArgumentException("FoodChain cannot be null");
            }

            foodChains.replace(mockedFoodChain.getId(), mockedFoodChain);
            return mockedFoodChain;
        });

        when(foodChainDao.deleteFoodChain(any(FoodChain.class))).then(invoke -> {
            FoodChain mockedFoodChain  = invoke.getArgumentAt(0, FoodChain.class);

            if (mockedFoodChain == null) {
                throw new IllegalArgumentException("FoodChain cannot be null");
            }

            foodChains.remove(mockedFoodChain.getId(), mockedFoodChain);
            return mockedFoodChain;
        });

        when(foodChainDao.getFoodChain(any(Long.class))).then(invoke -> {
            Long id  = invoke.getArgumentAt(0, Long.class);

            if (id == null) {
                throw new IllegalArgumentException("Id cannot be null");
            }

            return foodChains.get(id);
        });

        when(foodChainDao.getAllFoodChains()).then(invoke -> Collections.unmodifiableCollection(new ArrayList<>(foodChains.values())));
    }

}
