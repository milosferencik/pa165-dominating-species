package cz.muni.fi.pa165.dominatingspecies.service.services.implementations;

import cz.muni.fi.pa165.dominatingspecies.service.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.AnimalService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Animal;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
import cz.muni.fi.pa165.dominatingspecies.dao.interfaces.AnimalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  Ondrej Slimak on 18/04/2020.
 */

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalDao animalDao;

    @Override
    public void createAnimal(Animal animal) throws ServiceDataAccessException {
        try {
            animalDao.createAnimal(animal);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not create animal '" + ( animal == null ? "null" : animal.getName()) + "'", e);
        }
    }

    @Override
    public Animal getAnimal(long id) throws ServiceDataAccessException {
        try {
            return animalDao.getAnimal(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find animal with id " + id, e);
        }
    }

    @Override
    public List<Animal> getAllAnimals() throws ServiceDataAccessException {
        try {
            return animalDao.getAllAnimals();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not get all animals", e);
        }
    }

    @Override
    public List<Animal> getAnimalsByEnvironment(Environment environment) throws ServiceDataAccessException {
        try {
            return animalDao.getAllAnimalsInEnvironment(environment);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find animals in environtment '" + (environment == null ? " null" : environment.getName() + "' with id " + environment.getId()) , e);
        }
    }

    @Override
    public void updateAnimal(Animal animal) throws ServiceDataAccessException {
        try {
            animalDao.updateAnimal(animal);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not update animal '" + ( animal == null ? "null" : animal.getName() + "' with id " + animal.getId()), e);
        }
    }

    @Override
    public void deleteAnimal(Long id) throws ServiceDataAccessException {
        try {
            animalDao.deleteAnimal(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not remove animal with id " + id, e);
        }
    }
}
