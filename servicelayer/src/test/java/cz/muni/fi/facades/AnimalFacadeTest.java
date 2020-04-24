package cz.muni.fi.facades;

import cz.muni.fi.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AnimalFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AnimalFacade animalFacade;

}
