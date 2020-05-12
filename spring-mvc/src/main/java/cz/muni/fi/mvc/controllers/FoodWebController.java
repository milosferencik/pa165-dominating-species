package cz.muni.fi.mvc.controllers;


import cz.muni.fi.dto.AnimalListDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.EnvironmentListDTO;
import cz.muni.fi.facades.AnimalFacade;
import cz.muni.fi.facades.EnvironmentFacade;
import cz.muni.fi.facades.FoodWebFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;


@Controller
@RequestMapping("/foodWeb")
public class FoodWebController {

    @Autowired
    private EnvironmentFacade environmentFacade;

    @Autowired
    private AnimalFacade animalFacade;

    @Autowired
    private FoodWebFacade foodWebFacade;

    @ModelAttribute("environments")
    public List<EnvironmentListDTO> environemnts() {
        return environmentFacade.getAllEnvironment();
    }

    @ModelAttribute("animals")
    public List<AnimalListDTO> positions() {
        return animalFacade.getAllAnimals();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getFoodWebFromAllFoodChains(Model model) {
        model.addAttribute("foodWeb", foodWebFacade.getFoodWebFromAllFoodChains());
        return "foodWeb/index";
    }

    @RequestMapping(value = "/environment/{id}", method = RequestMethod.POST)
    public String getFoodWebByEnvironment(@PathVariable long id, Model model) {
        EnvironmentDTO env = environmentFacade.getEnvironmentById(id);
        model.addAttribute("foodWeb", foodWebFacade.getFoodWebByEnvironment(env));
        return "foodWeb/index";
    }
}
