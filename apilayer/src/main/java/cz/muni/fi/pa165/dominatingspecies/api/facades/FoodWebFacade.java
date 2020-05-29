package cz.muni.fi.pa165.dominatingspecies.api.facades;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodWebDTO;

/**
 * @author  Ondrej Slimak on 24/04/2020.
 */
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
    FoodWebDTO getFoodWebByAnimal(AnimalDTO animal);
}
