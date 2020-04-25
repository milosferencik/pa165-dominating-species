package cz.muni.fi.facades;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dto.*;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.EnvironmentService;
import dao.entities.Environment;
import org.hibernate.service.spi.ServiceException;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
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
 * Created by Kostka on 25/04/2020.
 */
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class EnvironmentFacadeTest {

    @Mock
    private EnvironmentService environmentService;

    @Spy
    @Autowired
    private BeanMappingService beanMappingService;

    @InjectMocks
    private EnvironmentFacade environmentFacade = new EnvironmentFacadeImpl();

    @BeforeClass
    public void init() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Environment dam;
    private Environment marsh;

    private List<Environment> environmentList;

    @BeforeMethod
    public void setup() throws ServiceException {
        marsh = new Environment();
        marsh.setName("Marsh");
        marsh.setDescription("45 % of water and 55 % of mug");

        dam = new Environment();
        dam.setName("Dam");
        dam.setDescription("95 % of fresh water and 5 % of mug");

        environmentList = new ArrayList<>();
        environmentList.add(marsh);
        environmentList.add(dam);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(environmentService);
    }

    @Test
    public void createEnvironmentTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Environment environment = invocationOnMock.getArgumentAt(0, Environment.class);
            environment.setId(2L);
            return environment;
        }).when(environmentService).createEnvironment(any(Environment.class));

        EnvironmentCreateDTO damCreateDTO = new EnvironmentCreateDTO();
        damCreateDTO.setName(dam.getName());
        damCreateDTO.setDescription(dam.getDescription());
        Long id = environmentFacade.createEnvironment(damCreateDTO);
        assertThat(id).isEqualTo(2L);
    }

    @Test
    public void updateEnvironmentTest() {
        EnvironmentDTO damDTO = beanMappingService.mapTo(dam, EnvironmentDTO.class);
        ArgumentCaptor<Environment> argument = ArgumentCaptor.forClass(Environment.class);
        environmentFacade.updateEnvironment(damDTO);
        Mockito.verify(environmentService).updateEnvironment(argument.capture());
        assertThat(argument.getValue()).isEqualToComparingFieldByField(dam);
    }

    @Test
    public void deleteEnvironmentTest() {
        ArgumentCaptor<Environment> argument = ArgumentCaptor.forClass(Environment.class);
        environmentFacade.deleteEnvironment(dam.getId());
        Mockito.verify(environmentService).deleteEnvironment(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(dam.getId());

    }

    @Test
    public void getEnvironmentByIdTest() {
        Mockito.when(environmentService.getEnvironment(dam.getId())).thenReturn(dam);
        EnvironmentDTO environmentDTO = environmentFacade.getEnvironmentById(dam.getId());
        assertThat(environmentDTO).isEqualToComparingFieldByField(beanMappingService.mapTo(dam, EnvironmentDTO.class));
    }

    @Test
    public void getAllAnimalsTest() {
        Mockito.when(environmentService.getAllEnvironments()).thenReturn(environmentList);
        List<EnvironmentListDTO> environmentListDTOS = environmentFacade.getAllEnvironment();
        assertThat(environmentListDTOS).isEqualTo(beanMappingService.mapTo(environmentList, EnvironmentListDTO.class));
    }
}
