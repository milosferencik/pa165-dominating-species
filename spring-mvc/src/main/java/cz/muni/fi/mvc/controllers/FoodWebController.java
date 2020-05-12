package cz.muni.fi.mvc.controllers;


import cz.muni.fi.dto.*;
import cz.muni.fi.facades.AnimalFacade;
import cz.muni.fi.facades.EnvironmentFacade;
import cz.muni.fi.facades.FoodWebFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    public List<EnvironmentListDTO> environments() {
        return environmentFacade.getAllEnvironment();
    }

    @ModelAttribute("animals")
    public List<AnimalListDTO> positions() {
        return animalFacade.getAllAnimals();
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getFoodWebFromAllFoodChains(Model model) {
        model.addAttribute("foodWebTitle", "Food Web");
        model.addAttribute("foodWeb", foodWebFacade.getFoodWebFromAllFoodChains());
        return "foodWeb/index";
    }

    @RequestMapping(value = "/environment", method = RequestMethod.POST)
    public String getFoodWebByEnvironmentFormHandler (@RequestParam("environmentId") Long id, UriComponentsBuilder uriBuilder) {
        if (id == 0) {
            return "redirect:" + uriBuilder.path("/foodWeb/").encode().toUriString();
        }
        return "redirect:" + uriBuilder.path("/foodWeb/environment/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/environment/{id}", method = RequestMethod.GET)
    public String getFoodWebByEnvironment(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        EnvironmentDTO env = environmentFacade.getEnvironmentById(id);
        if (env == null) {
            return "redirect:" + uriBuilder.path("/foodWeb/").encode().toUriString();
        }

        model.addAttribute("foodWebTitle", "Food Web of environment " + env.getName());
        model.addAttribute("selectedEnvironmentId", id);
        model.addAttribute("foodWeb", foodWebFacade.getFoodWebByEnvironment(env));
        return "foodWeb/index";
    }


    @RequestMapping(value = "/animal", method = RequestMethod.POST)
    public String getFoodWebByAnimalFormHandler (@RequestParam("animalId") Long id, UriComponentsBuilder uriBuilder) {
        if (id == 0) {
            return "redirect:" + uriBuilder.path("/foodWeb/").encode().toUriString();
        }
        return "redirect:" + uriBuilder.path("/foodWeb/animal/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/animal/{id}", method = RequestMethod.GET)
    public String getFoodWebByAnimal(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        AnimalDTO animal = animalFacade.getAnimalById(id);
        if (animal == null) {
            return "redirect:" + uriBuilder.path("/foodWeb/").encode().toUriString();
        }

        FoodWebDTO foodWeb = foodWebFacade.getFoodWebByAnimal(animal);
        expandFoodWeb(foodWeb);

        model.addAttribute("foodWebTitle", "Food Web of animal " + animal.getName());
        model.addAttribute("selectedAnimalId", id);
        model.addAttribute("foodWeb", foodWeb);
        return "foodWeb/index";
    }

    /**
     * Expands food web dictionary so every animal of the feed web is present as a key.
     * This is done at presentation layer because of the presentation needs (JS) for this particular use case.
     */
    private void expandFoodWeb(FoodWebDTO foodWeb) {
        for (Map.Entry<AnimalListDTO, List<AnimalListDTO>> entry : foodWeb.foodWeb.entrySet())   {
            for (AnimalListDTO animal : entry.getValue()) {
                if (!foodWeb.foodWeb.containsKey(animal)) {
                    foodWeb.foodWeb.put(animal, new ArrayList<>());
                }
            }
        }
    }

}
