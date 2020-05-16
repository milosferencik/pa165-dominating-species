package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.FoodChainCreateDTO;
import cz.muni.fi.dto.FoodChainDTO;
import cz.muni.fi.facades.FoodChainFacade;
import cz.muni.fi.mvc.validators.EnvironmentCreateDtoValidator;
import cz.muni.fi.mvc.validators.EnvironmentUpdateDtoValidator;
import cz.muni.fi.mvc.validators.FoodChainCreateDtoValidator;
import cz.muni.fi.mvc.validators.FoodChainUpdateDtoValidator;
import dao.entities.FoodChain;
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
}
