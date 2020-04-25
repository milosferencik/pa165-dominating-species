package cz.muni.fi.facades;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dto.*;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.EnvironmentService;
import cz.muni.fi.services.interfaces.UserService;
import dao.entities.Environment;
import dao.entities.User;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

/**
 * @author Kostka on 25/04/2020.
 */
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserService userService;

    @Spy
    @Autowired
    private BeanMappingService beanMappingService;

    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private User u1;

    @BeforeMethod
    public void setup() throws ServiceException {
        u1 = new User();
        u1.setName("Jane");
        u1.setSurname("Doe");
        u1.setEmail("janedoe@muni.cz");
        u1.setPasswordHash("955db0b81ef1989b4a4dfeae8061a9a6");
        u1.setAdmin(false);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(userService);
    }

    @Test
    public void createUserTest() {
        Mockito.doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgumentAt(0, User.class);
            user.setId(2L);
            return user;
        }).when(userService).createUser(any(User.class));

        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName(u1.getName());
        userCreateDTO.setAdmin(u1.isAdmin());
        userCreateDTO.setEmail(u1.getEmail());
        userCreateDTO.setSurname(u1.getSurname());
        userCreateDTO.setPasswordHash(u1.getPasswordHash());
        Long id = userFacade.createUser(userCreateDTO);
        assertThat(id).isEqualTo(2L);
    }

    @Test
    public void updateEnvironmentTest() {
        UserDTO userDTO = beanMappingService.mapTo(u1, UserDTO.class);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        userFacade.updateUser(userDTO);
        Mockito.verify(userService).updateUser(argument.capture());
        assertThat(argument.getValue()).isEqualToComparingFieldByField(u1);
    }

    @Test
    public void deleteEnvironmentTest() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        userFacade.deleteUser(u1.getId());
        Mockito.verify(userService).deleteUser(argument.capture());
        assertThat(argument.getValue()).isEqualTo(u1.getId());

    }

    @Test
    public void getEnvironmentByIdTest() {
        Mockito.when(userService.getUser(u1.getId())).thenReturn(u1);
        UserDTO userDTO = userFacade.getUserById(u1.getId());
        assertThat(userDTO).isEqualToComparingFieldByField(beanMappingService.mapTo(u1, UserDTO.class));
    }
}
