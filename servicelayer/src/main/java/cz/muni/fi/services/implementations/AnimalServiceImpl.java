package cz.muni.fi.services.implementations;

import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.AnimalService;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.interfaces.AnimalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ondrej Slimak on 18/04/2020.
 */

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalDao animalDao;

    @Override
    public void create(Animal animal) throws ServiceDataAccessException {
        try {
            animalDao.createAnimal(animal);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not create animal '" + ( animal == null ? "null" : animal.getName()) + "'", e);
        }
    }

    @Override
    public Animal findById(long id) throws ServiceDataAccessException {
        try {
            return animalDao.getAnimal(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find animal with id " + id, e);
        }
    }

    @Override
    public List<Animal> findAll() throws ServiceDataAccessException {
        try {
            return animalDao.getAllAnimals();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not get all animals", e);
        }
    }

    @Override
    public List<Animal> findAnimalsByEnvironment(Environment environment) throws ServiceDataAccessException {
        try {
            return animalDao.getAllAnimalsInEnvironment(environment);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find animals in environtment '" + (environment == null ? " null" : environment.getName() + "' with id " + environment.getId()) , e);
        }
    }

    @Override
    public void update(Animal animal) throws ServiceDataAccessException {
        try {
            animalDao.updateAnimal(animal);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not update animal '" + ( animal == null ? "null" : animal.getName() + "' with id " + animal.getId()), e);
        }
    }

    @Override
    public void remove(Animal animal) throws ServiceDataAccessException {
        try {
            animalDao.deleteAnimal(animal);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not remove animal '" + ( animal == null ? "null" : animal.getName() + "' with id " + animal.getId()), e);
        }
    }
}
