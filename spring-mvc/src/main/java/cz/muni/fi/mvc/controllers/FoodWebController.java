package cz.muni.fi.mvc.controllers;


import cz.muni.fi.facades.AnimalFacade;
import cz.muni.fi.facades.EnvironmentFacade;
import cz.muni.fi.facades.FoodWebFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/foodWeb")
public class FoodWebController {

    @Autowired
    private EnvironmentFacade environmentFacade;

    @Autowired
    private AnimalFacade animalFacade;

    @Autowired
    private FoodWebFacade foodWebFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getFoodWebFromAllFoodChains(Model model) {
        model.addAttribute("environments", environmentFacade.getAllEnvironment());
        model.addAttribute("animals", animalFacade.getAllAnimals());
        model.addAttribute("foodWeb", foodWebFacade.getFoodWebFromAllFoodChains());
        return "foodWeb/index";
    }
}
