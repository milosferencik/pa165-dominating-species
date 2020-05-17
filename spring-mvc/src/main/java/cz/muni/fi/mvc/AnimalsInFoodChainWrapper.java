package cz.muni.fi.mvc;

import cz.muni.fi.dto.AnimalListDTO;

import java.util.ArrayList;

/**
 * @author Ondrej Slimak
 */
public class AnimalsInFoodChainWrapper {
    ArrayList<AnimalListDTO> animals;

    public ArrayList<AnimalListDTO> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<AnimalListDTO> animals) {
        this.animals = animals;
    }
}
