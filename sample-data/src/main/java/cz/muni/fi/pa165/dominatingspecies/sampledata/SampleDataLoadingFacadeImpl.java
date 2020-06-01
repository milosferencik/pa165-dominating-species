package cz.muni.fi.pa165.dominatingspecies.sampledata;

import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.AnimalService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.EnvironmentService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.FoodChainService;
import cz.muni.fi.pa165.dominatingspecies.service.services.interfaces.UserService;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Animal;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.FoodChain;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Milos Ferencik
 */

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private FoodChainService foodChainService;

    @Autowired
    private UserService userService;

    @Override
    public void loadData() throws IOException {
        Environment field = environment("Field", "Regular field");
        Environment forest = environment("Forest", "50 % of coniferous trees and 50 % broadleaved trees ");
        Environment pond = environment("Pond", "Pond or marsh surrounded with tall grass.");
        log.info("Loaded environments.");

        Animal insect = animal("Insect", "Grasshopper", field);
        Animal vole = animal("Vole", "Bank Vole", field);
        Animal hawk = animal("Hawk", "Red-tailed Hawk", field);
        Animal fox = animal("Fox", "Red Fox", forest);
        Animal wolf = animal("Wolf", "Grey Wolf", forest);
        Animal deer = animal("Deer", "Roe deer", forest);
        Animal frog = animal("Frog", "European grass frog", pond);
        Animal snake = animal("Snake", "Ring-necked snake", field);
        log.info("Loaded animals.");

        foodChain(Arrays.asList(insect, vole, hawk));
        foodChain(Arrays.asList(insect, vole, fox, wolf));
        foodChain(Arrays.asList(insect, frog, fox, wolf));
        foodChain(Arrays.asList(deer, wolf));
        foodChain(Arrays.asList(insect, frog, snake, hawk));
        foodChain(Arrays.asList(insect, vole, snake));
        log.info("Loaded food chains.");

        user("Jane", "Doe", "janedoe@muni.cz", false, "password");
        user("John","Doe", "johndoe@muni.cz", true, "admin");
        log.info("Loaded users.");
    }

    private Environment environment(String name, String description) {
        Environment env = new Environment();
        env.setName(name);
        env.setDescription(description);
        environmentService.createEnvironment(env);
        return env;
    }

    private Animal animal(String name, String species, Environment environment) {
        Animal ani = new Animal();
        ani.setName(name);
        ani.setSpecies(species);
        ani.setEnvironment(environment);
        animalService.createAnimal(ani);
        return ani;
    }

    private FoodChain foodChain(List<Animal> animalList) {
        FoodChain fch = new FoodChain();
        fch.setAnimals(animalList);
        foodChainService.createFoodChain(fch);
        return fch;
    }

    private User user(String name, String surname, String email, boolean isAdmin, String password) {
        User usr = new User();
        usr.setName(name);
        usr.setSurname(surname);
        usr.setEmail(email);
        usr.setAdmin(isAdmin);
        userService.createUser(usr, password);
        return usr;
    }
}
