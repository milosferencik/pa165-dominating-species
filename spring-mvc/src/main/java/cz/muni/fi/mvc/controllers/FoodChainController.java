package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.*;
import cz.muni.fi.facades.AnimalFacade;
import cz.muni.fi.facades.FoodChainFacade;
import cz.muni.fi.mvc.validators.FoodChainCreateDtoValidator;
import cz.muni.fi.mvc.validators.FoodChainUpdateDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

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

        redirectAttributes.addFlashAttribute("alert_success", "FoodChain with id" + foodChainDTO.getId() + " was successfully deleted");
        return "redirect:" + uriBuilder.path("/foodChain/list").toUriString();
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
        model.addAttribute("foodChain", foodChainFacade.getFoodChainById(id));
        model.addAttribute("animals", foodChainFacade.getFoodChainById(id).getAnimalsInFoodChain());
        return "foodChain/detail";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("create()");
        model.addAttribute("foodChainCreate", new FoodChainCreateDTO());
        return "foodChain/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("foodChainCreate") FoodChainCreateDTO foodChainCreateDTO,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder urisBuilder) {
        log.debug("create(foodChainCreate={})", foodChainCreateDTO);
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe: bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "foodChain/create";
        }

        Long id = foodChainFacade.createFoodChain(foodChainCreateDTO);

        redirectAttributes.addFlashAttribute("alert_success", "FoodChain " + id + " was created successfully");
        return "redirect:" + urisBuilder.path("/foodChain/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        log.debug("update()");
        model.addAttribute("foodChainUpdate", foodChainFacade.getFoodChainById(id));
        return "foodChain/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("foodChainUpdate") FoodChainDTO foodChainDTO,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder urisBuilder) {
        log.debug("update(environmentUpdate={})", foodChainDTO);
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe: bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "foodChain/update";
        }

        foodChainFacade.updateFoodChain(foodChainDTO);

        redirectAttributes.addFlashAttribute("alert_success", "FoodChain " +foodChainDTO.getId() + " was updated successfully");
        return "redirect:" + urisBuilder.path("/foodChain/detail/{id}").buildAndExpand(foodChainDTO.getId()).encode().toUriString();
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
}
