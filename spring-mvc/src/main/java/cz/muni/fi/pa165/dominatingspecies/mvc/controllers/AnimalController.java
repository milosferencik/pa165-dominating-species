package cz.muni.fi.pa165.dominatingspecies.mvc.controllers;


import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalUpdateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentListDTO;
import cz.muni.fi.pa165.dominatingspecies.api.facades.AnimalFacade;
import cz.muni.fi.pa165.dominatingspecies.api.facades.EnvironmentFacade;
import cz.muni.fi.pa165.dominatingspecies.mvc.validators.AnimalCreateDtoValidator;
import cz.muni.fi.pa165.dominatingspecies.mvc.validators.AnimalUpdateDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Milos Ferencik
 */

@Controller
@RequestMapping("/animal")
public class AnimalController {

    final static Logger log = LoggerFactory.getLogger(AnimalController.class);

    @Autowired
    private AnimalFacade animalFacade;

    @Autowired
    private EnvironmentFacade environmentFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("animals", animalFacade.getAllAnimals());
        return "animal/list";
    }

    @RequestMapping(value = "/environment", method = RequestMethod.POST)
    public String getAnimalsByEnvironmentFormHandler(@RequestParam("environmentId") Long id, UriComponentsBuilder uriBuilder) {
        if (id == 0) {
            return "redirect:" + uriBuilder.path("/animal/").encode().toUriString();
        }
        return "redirect:" + uriBuilder.path("/animal/environment/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/environment/{id}", method = RequestMethod.GET)
    public String getAnimalsByEnvironment(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        log.debug("getAnimalsByEnvironment({})", id);
        model.addAttribute("selectedEnvironmentId", id);
        EnvironmentDTO env = environmentFacade.getEnvironmentById(id);
        if (env == null) {
            return "redirect:" + uriBuilder.path("/animal/").encode().toUriString();
        }

        model.addAttribute("animals", animalFacade.getAnimalsByEnvironment(env.getId()));
        return "animal/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        AnimalDTO animal = animalFacade.getAnimalById(id);
        log.debug("delete({})", id);
        try {
            animalFacade.deleteAnimal(id);
            redirectAttributes.addFlashAttribute("alert_success", "Animal \"" + animal.getName() + "\" was deleted.");
        } catch (Exception ex) {
            log.error("animal "+id+" cannot be deleted (it is included in an foodChain)");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Animal \"" + animal.getName() + "\" cannot be deleted.");
        }
        return "redirect:" + uriBuilder.path("/animal/").toUriString();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        log.debug("detail({})", id);
        model.addAttribute("animal", animalFacade.getAnimalById(id));
        return "animal/detail";
    }

    @ModelAttribute("environments")
    public List<EnvironmentListDTO> environments() {
        log.debug("environments()");
        return environmentFacade.getAllEnvironment();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof AnimalCreateDTO) {
            binder.addValidators(new AnimalCreateDtoValidator());
        }
        if (binder.getTarget() instanceof AnimalUpdateDTO) {
            binder.addValidators(new AnimalUpdateDtoValidator());
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("create()");
        model.addAttribute("animalCreate", new AnimalCreateDTO());
        return "animal/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("animalCreate") AnimalCreateDTO animal,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        log.debug("create(animalCreate={})", animal);
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "animal/create";
        }

        Long id;
        try {
            id = animalFacade.createAnimal(animal);
        } catch (Exception e) {
            model.addAttribute("alert_danger", "Animal " + animal.getName() + " already exists.");
            return "animal/create";
        }
        redirectAttributes.addFlashAttribute("alert_success", "Animal " + animal.getName() + " was created");
        return "redirect:" + uriBuilder.path("/animal/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {
        log.debug("update()");
        AnimalUpdateDTO animalUpdateDto = animalFacade.getAnimalUpdateById(id);
        model.addAttribute("animalUpdate", animalUpdateDto);
        return "animal/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("animalUpdate") AnimalUpdateDTO animal,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        log.debug("update(animalUpdate={})", animal);
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "animal/update";
        }

        try {
            animalFacade.updateAnimal(animal);
        } catch (Exception e) {
            model.addAttribute("alert_danger", "Animal " + animal.getName() + " already exists.");
            return "animal/update";
        }
        redirectAttributes.addFlashAttribute("alert_success", "Animal " + animal.getName() + " was updated");
        return "redirect:" + uriBuilder.path("/animal/detail/{id}").buildAndExpand(animal.getId()).encode().toUriString();
    }

}
