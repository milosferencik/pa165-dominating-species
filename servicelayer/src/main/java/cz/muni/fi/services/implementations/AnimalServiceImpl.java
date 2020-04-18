package cz.muni.fi.services.implementations;

import cz.muni.fi.services.interfaces.AnimalService;
import dao.entities.Animal;
import dao.interfaces.AnimalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalDao animalDao;

    @Override
    public void create(Animal animal) {
        animalDao.createAnimal(animal);
    }

    @Override
    public Animal findById(long id) {
        return animalDao.getAnimal(id);
    }

    @Override
    public List<Animal> findAll() {
        return animalDao.getAllAnimals();
    }

    @Override
    public void update(Animal animal) {
        animalDao.updateAnimal(animal);
    }

    @Override
    public void remove(Animal animal) {
        animalDao.deleteAnimal(animal);
    }
}
