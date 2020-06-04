package cz.muni.fi.pa165.dominatingspecies.service.services;


import cz.muni.fi.pa165.dominatingspecies.api.dto.UserDTO;
import cz.muni.fi.pa165.dominatingspecies.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.BeanMappingService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Milos Ferencik 24/4/2020
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    private User user = new User();

    @BeforeMethod
    public void createUser(){
        user.setName("Jane");
        user.setSurname("Doe");
        user.setEmail("janedoe@muni.cz");
        user.setPasswordHash("955db0b81ef1989b4a4dfeae8061a9a6");
        user.setAdmin(false);
    }

    @Test
    public void shouldMapUserCorrect(){
        UserDTO userDTO = beanMappingService.mapTo(user, UserDTO.class);
        assertThat(userDTO).isEqualToComparingFieldByField(user);
    }
}
