package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalCreateDTO;
import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.dto.AnimalListDTO;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.EnvironmentService;
import dao.entities.Animal;
import dao.entities.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Milos Ferencik 24/4/2020
 */

@Service
@Transactional
public class AnimalFacadeImpl implements AnimalFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private EnvironmentService environmentService;

    @Override
    public Long createAnimal(AnimalCreateDTO animalCreateDTO) {
        Animal animal = beanMappingService.mapTo(animalCreateDTO, Animal.class);
        Environment environment = environmentService.getEnvironment(animalCreateDTO.getEnvironmentId());
        animal.setEnvironment(environment);
        animalService.createAnimal(animal);
        return animal.getId();
    }

    @Override
    public void updateAnimal(AnimalDTO animalDTO) {
        Animal newAnimal = beanMappingService.mapTo(animalDTO, Animal.class);
        animalService.updateAnimal(newAnimal);
    }

    @Override
    public void deleteAnimal(Long id) {
        animalService.deleteAnimal(id);
    }

    @Override
    public List<AnimalListDTO> getAllAnimals() {
        return beanMappingService.mapTo(animalService.getAllAnimals(), AnimalListDTO.class);
    }

    @Override
    public List<AnimalListDTO> getAnimalsByEnvironment(Long environmentId) {
        Environment environment = environmentService.getEnvironment(environmentId);
        return beanMappingService.mapTo(animalService.getAnimalsByEnvironment(environment), AnimalListDTO.class);
    }

    @Override
    public AnimalDTO getAnimalById(Long id) {
        Animal animal = animalService.getAnimal(id);
        return (animal == null) ? null : beanMappingService.mapTo(animal, AnimalDTO.class);
    }
}
