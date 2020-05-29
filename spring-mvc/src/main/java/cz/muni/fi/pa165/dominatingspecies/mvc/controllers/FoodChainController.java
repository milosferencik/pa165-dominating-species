package cz.muni.fi.pa165.dominatingspecies.mvc.controllers;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalInFoodChainDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalListDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainDTO;
import cz.muni.fi.pa165.dominatingspecies.api.facades.AnimalFacade;
import cz.muni.fi.pa165.dominatingspecies.api.facades.FoodChainFacade;
import cz.muni.fi.pa165.dominatingspecies.mvc.validators.FoodChainCreateDtoValidator;
import cz.muni.fi.pa165.dominatingspecies.mvc.validators.FoodChainUpdateDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Petr Kostka
 */
@Controller
@RequestMapping("/foodChain")
public class FoodChainController {

    @Autowired
    private FoodChainFacade foodChainFacade;

    @Autowired
    private AnimalFacade animalFacade;

    final static Logger log = LoggerFactory.getLogger(FoodChainController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("foodChains", foodChainFacade.getAllFoodChains());
        return "foodChain/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
                         Model model,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        FoodChainDTO foodChainDTO = foodChainFacade.getFoodChainById(id);
        foodChainFacade.deleteFoodChain(id);

        redirectAttributes.addFlashAttribute("alert_success", "FoodChain with id " + foodChainDTO.getId() + " was successfully deleted");
        return "redirect:" + uriBuilder.path("/foodChain/").toUriString();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof FoodChainCreateDTO) {
            binder.addValidators(new FoodChainCreateDtoValidator());
        }
        if (binder.getTarget() instanceof FoodChainDTO) {
            binder.addValidators(new FoodChainUpdateDtoValidator());
        }
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        log.debug("detail({})", id);
        List<AnimalInFoodChainDTO> animalInFoodChainDTOS = foodChainFacade.getFoodChainById(id).getAnimalsInFoodChain();
        model.addAttribute("foodChain", foodChainFacade.getFoodChainById(id));
        List<AnimalListDTO> animalNotInFoodChainDTOS = new ArrayList<>();
        boolean contained = true;

        for (AnimalListDTO a : animalFacade.getAllAnimals()){
            for(AnimalInFoodChainDTO dto : animalInFoodChainDTOS){
                if (Objects.equals(a.getId(), dto.getAnimal().getId())){
                    contained = true;
                    break;
                }
                else{
                    contained = false;
                }
            }
            if (!contained){
                contained = true;
                animalNotInFoodChainDTOS.add(a);
            }
        }
        model.addAttribute("animalsNotInFoodChain", animalNotInFoodChainDTOS);
        model.addAttribute("animalsInFoodChain", animalInFoodChainDTOS);
        return "foodChain/detail";
    }


    @RequestMapping(value = "/animal", method = RequestMethod.POST)
    public String getFoodChainsByAnimalFormHandler(@RequestParam("animalId") Long id, UriComponentsBuilder uriBuilder) {
        if (id == 0) {
            return "redirect:" + uriBuilder.path("/foodChain/").encode().toUriString();
        }
        return "redirect:" + uriBuilder.path("/foodChain/animal/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/animal/{id}", method = RequestMethod.GET)
    public String getFoodChainsByAnimal(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        log.debug("getFoodChainsByAnimal({})", id);
        model.addAttribute("selectedAnimalId", id);
        AnimalDTO animalDTO = animalFacade.getAnimalById(id);
        if (animalDTO == null) {
            return "redirect:" + uriBuilder.path("/foodChain/").encode().toUriString();
        }

        model.addAttribute("foodChains", foodChainFacade.getFoodChainsWithAnimal(animalDTO.getId()));
        return "foodChain/list";
    }
    @ModelAttribute("animals")
    public List<AnimalListDTO> animals() {
        log.debug("animals()");
        return animalFacade.getAllAnimals();
    }
}
