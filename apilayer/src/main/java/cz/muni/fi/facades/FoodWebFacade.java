package cz.muni.fi.facades;

import cz.muni.fi.dto.AnimalListDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.FoodWebDTO;

public interface FoodWebFacade {

    /**
     * Build FoodWeb from all stored food chains
     * @return food web with all animals
     */
    FoodWebDTO getFoodWebFromAllFoodChains();

    /**
     * Build FoodWeb with animals of given environment
     * @param environment environment of animals from which the food web will be build
     * @return food web of animals of given environment
     */
    FoodWebDTO getFoodWebByEnvironment(EnvironmentDTO environment);

    /**
     * Build FoodWeb from all food chains with targeted animal
     * @param animal targeted animal
     * @return food web with targeted animal
     */
    FoodWebDTO getFoodWebByAnimal(AnimalListDTO animal);
}