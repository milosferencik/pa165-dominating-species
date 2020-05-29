package cz.muni.fi.pa165.dominatingspecies.service.facades;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalListDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalUpdateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.facades.AnimalFacade;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.AnimalService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.BeanMappingService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.EnvironmentService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Animal;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
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
    public void updateAnimal(AnimalUpdateDTO animalUpdateDto) {
        Animal newAnimal = beanMappingService.mapTo(animalUpdateDto, Animal.class);
        Environment environment = environmentService.getEnvironment(animalUpdateDto.getEnvironmentId());
        newAnimal.setEnvironment(environment);
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

    @Override
    public AnimalUpdateDTO getAnimalUpdateById(Long id) {
        Animal animal = animalService.getAnimal(id);
        if (animal == null)
            return null;
        AnimalUpdateDTO animalUpdateDTO = beanMappingService.mapTo(animal, AnimalUpdateDTO.class);
        animalUpdateDTO.setEnvironmentId(animal.getEnvironment().getId());
        return animalUpdateDTO;
    }
}
