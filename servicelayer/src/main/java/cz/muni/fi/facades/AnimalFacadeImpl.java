package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalCreateDTO;
import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.BeanMappingService;
import cz.muni.fi.services.interfaces.EnvironmentService;
import dao.entities.Animal;
import dao.entities.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        Animal animal = new Animal();
        animal.setId(id);
        animalService.deleteAnimal(animal);
    }

    @Override
    public List<AnimalDTO> getAllAnimals() {
        return beanMappingService.mapTo(animalService.getAllAnimals(), AnimalDTO.class);
    }

    @Override
    public List<AnimalDTO> getAnimalsByEnvironment(Long environmentId) {
        Environment environment = environmentService.getEnvironment(environmentId);
        return beanMappingService.mapTo(animalService.getAnimalsByEnvironment(environment), AnimalDTO.class);
    }

    @Override
    public AnimalDTO getAnimalById(Long id) {
        Animal animal = animalService.getAnimal(id);
        return (animal == null) ? null : beanMappingService.mapTo(animal, AnimalDTO.class);
    }
}
