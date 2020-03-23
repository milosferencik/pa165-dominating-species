package dao.interfaces;

import dao.entities.Animal;

import java.util.List;

/**
 * Created by Kostka on 23/03/2020.
 */
public interface AnimalDao {
    public void createAnimal(Animal animal);
    public List<Animal> getAllAnimals();
    public Animal getAnimal(Long id);
    public void updateAnimal(Animal animal);
    public void deleteAnimal(Animal animal);
}
