package cz.muni.fi.pa165.dominatingspecies.service.facades;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AuthenticateUserDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.UserCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.UserDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.UserUpdateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.facades.UserFacade;
import cz.muni.fi.pa165.dominatingspecies.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.BeanMappingService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.UserService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.User;
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

import java.util.Collections;
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
        u1.setPasswordHash("pass");
        u1.setId(1L);
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
        }).when(userService).createUser(any(User.class), any(String.class));

        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName(u1.getName());
        userCreateDTO.setAdmin(u1.isAdmin());
        userCreateDTO.setEmail(u1.getEmail());
        userCreateDTO.setSurname(u1.getSurname());
        userCreateDTO.setPassword(u1.getPasswordHash());
        Long id = userFacade.createUser(userCreateDTO, "password");
        assertThat(id).isEqualTo(2L);
    }

    @Test
    public void updateUserTest() {
        u1.setSurname("NewSurname");
        Mockito.when(userService.getUser(u1.getId())).thenReturn(u1);
        UserUpdateDTO userUpdateDTO = beanMappingService.mapTo(u1, UserUpdateDTO.class);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        userFacade.updateUser(userUpdateDTO);
        Mockito.verify(userService).updateUser(argument.capture());
        assertThat(argument.getValue()).isEqualToComparingFieldByField(u1);
    }

    @Test
    public void deleteUserTest() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        userFacade.deleteUser(u1.getId());
        Mockito.verify(userService).deleteUser(argument.capture());
        assertThat(argument.getValue()).isEqualTo(u1.getId());
    }

    @Test
    public void getUserByIdTest() {
        Mockito.when(userService.getUser(u1.getId())).thenReturn(u1);
        UserDTO userDTO = userFacade.getUserById(u1.getId());
        assertThat(userDTO).isEqualToComparingFieldByField(beanMappingService.mapTo(u1, UserDTO.class));
    }

    @Test
    public void getUserByEmailTest() {
        Mockito.when(userService.getUserByEmail(u1.getEmail())).thenReturn(u1);
        UserDTO userDTO = userFacade.getUserByEmail(u1.getEmail());
        assertThat(userDTO).isEqualToComparingFieldByField(beanMappingService.mapTo(u1, UserDTO.class));
    }

    @Test
    public void getAllUsersTest() {
        Mockito.when(userService.getAllUsers()).thenReturn(Collections.singletonList(u1));
        List<UserDTO> users = userFacade.getAllUsers();
        assertThat(users).isEqualTo(beanMappingService.mapTo(Collections.singletonList(u1), UserDTO.class));
    }

    @Test
    public void passwordChangeTest() {
        String password = "pass";
        String newPassword = "admin";
        UserDTO u1DTO = beanMappingService.mapTo(u1, UserDTO.class);

        Mockito.doReturn(u1).when(userService).getUser(u1.getId());
        Mockito.doReturn(true).when(userService).changePassword(u1, password, newPassword);
        AuthenticateUserDTO authenticateDTO = new AuthenticateUserDTO();
        authenticateDTO.setPassword(newPassword);
        assertThat(userFacade.changePassword(u1DTO, password, newPassword)).isTrue();
    }

    @Test
    public void authenticateUserTest() {
        String password = "pass";
        Mockito.doReturn(u1).when(userService).getUserByEmail(u1.getEmail());
        Mockito.doReturn(true).when(userService).authenticate(u1, password);
        AuthenticateUserDTO authenticateDTO = new AuthenticateUserDTO();
        authenticateDTO.setEmail(u1.getEmail());
        authenticateDTO.setPassword(password);
        assertThat(userFacade.authenticate(authenticateDTO)).isTrue();
        Mockito.verify(userService).authenticate(u1, password);
    }
}