package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.AnimalCreateDTO;
import cz.muni.fi.dto.AnimalDTO;
import cz.muni.fi.dto.EnvironmentListDTO;
import cz.muni.fi.facades.AnimalFacade;
import cz.muni.fi.facades.EnvironmentFacade;
import cz.muni.fi.mvc.validators.AnimalCreateDtoValidator;
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
        log.debug("view({})", id);
        model.addAttribute("animal", animalFacade.getAnimalById(id));
        return "animal/detail";
    }

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("create()");
        model.addAttribute("createAnimal", new AnimalCreateDTO());
        return "animal/create";
    }

    @ModelAttribute("environments")
    public List<EnvironmentListDTO> environments() {
        log.debug("environments()");
        return environmentFacade.getAllEnvironment();
    }

    /**
     * Spring Validator added to JSR-303 Validator for this @Controller only.
     * It is useful  for custom validations that are not defined on the form bean by annotations.
     * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof AnimalCreateDTO) {
            binder.addValidators(new AnimalCreateDtoValidator());
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("animalCreate") AnimalCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(animalCreate={})", formBean);
        //in case of validation error forward back to the the form
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
        //create animal
        Long id = animalFacade.createAnimal(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Animal " + id + " was created");
        return "redirect:" + uriBuilder.path("/animal/detail/{id}").buildAndExpand(id).encode().toUriString();
    }
}
