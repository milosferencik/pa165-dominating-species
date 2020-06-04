package cz.muni.fi.pa165.dominatingspecies.sampledata;


import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.AnimalService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.FoodChainService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Milos Ferencik
 */

@ContextConfiguration(classes = {SampleDataConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleDataLoadingFacadeTest extends AbstractTestNGSpringContextTests {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeTest.class);

    @Autowired
    public AnimalService animalService;

    @Autowired
    public FoodChainService foodChainService;

    @Autowired
    public UserService userService;

    @Autowired
    public SampleDataLoadingFacade sampleDataLoadingFacade;

    @Test
    public void createSampleData() throws IOException {
        log.debug("starting test");

        assertThat(animalService.getAllAnimals()).isNotEmpty();
        assertThat(foodChainService.getFoodChainsWithAnimal(animalService.getAllAnimals().get(0))).isNotEmpty();
        assertThat(userService.getAllUsers()).isNotEmpty();
    }
}
