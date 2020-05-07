package cz.muni.fi.sampledata;

import cz.muni.fi.services.interfaces.AnimalService;
import cz.muni.fi.services.interfaces.EnvironmentService;
import cz.muni.fi.services.interfaces.FoodChainService;
import cz.muni.fi.services.interfaces.UserService;
import dao.entities.Animal;
import dao.entities.Environment;
import dao.entities.FoodChain;
import dao.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

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
        log.info("Loaded environments.");

        Animal insect = animal("Insect", "Grasshopper", field);
        Animal vole = animal("Vole", "Bank Vole", field);
        Animal hawk = animal("Hawk", "Red-tailed Hawk", field);
        Animal fox = animal("Fox", "Red Fox", forest);
        Animal wolf = animal("Wolf", "Grey Wolf", forest);
        Animal deer =animal("Deer", "Roe deer", forest);
        log.info("Loaded animals.");

        foodChain(List.of(insect, vole, hawk));
        foodChain(List.of(insect, vole, fox, wolf));
        foodChain(List.of(deer, wolf));
        log.info("Loaded food chains.");

        user("Jane", "Doe", "janedoe@muni.cz", "955db0b81ef1989b4a4dfeae8061a9a6", false);
        user("John","Doe", "johndoe@muni.cz", "955db0b81ef1989b4a4dfeae8061a9b7", true);
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

    private User user(String name, String surname, String email, String passwordHash, boolean isAdmin) {
        User usr = new User();
        usr.setName(name);
        usr.setSurname(surname);
        usr.setEmail(email);
        usr.setPasswordHash(passwordHash);
        usr.setAdmin(isAdmin);
        userService.createUser(usr);
        return usr;
    }
}
