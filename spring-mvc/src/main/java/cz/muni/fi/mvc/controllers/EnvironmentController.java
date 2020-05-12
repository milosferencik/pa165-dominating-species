package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.facades.EnvironmentFacade;
import cz.muni.fi.mvc.validators.EnvironmentCreateDtoValidator;
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

import javax.validation.Valid;

/**
 * @author Ondrej Slimak
 */

@Controller
@RequestMapping("/environment")
public class EnvironmentController {

    private final static Logger log = LoggerFactory.getLogger(EnvironmentController.class);

    @Autowired
    private EnvironmentFacade environmentFacade;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("environments", environmentFacade.getAllEnvironment());
        return "environment/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
                         Model model,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        EnvironmentDTO env = environmentFacade.getEnvironmentById(id);
        log.debug("delete({})", id);
        try {
            environmentFacade.deleteEnvironment(id);
            redirectAttributes.addFlashAttribute("alert_success", "Environment " + env.getName() + " was successfully deleted");
        } catch (Exception ex) {
            log.error("Environment "+id+" cannot be deleted (it is included in an animal)");
            log.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Environment " + env.getName() + " cannot be deleted");
        }
        return "redirect:" + uriBuilder.path("/environment/").toUriString();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        log.debug("detail({})", id);
        model.addAttribute("environment", environmentFacade.getEnvironmentById(id));
        return "environment/detail";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof EnvironmentCreateDTO) {
            binder.addValidators(new EnvironmentCreateDtoValidator());
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("create()");
        model.addAttribute("environmentCreate", new EnvironmentCreateDTO());
        return "environment/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("environmentCreate") EnvironmentCreateDTO env,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder urisBuilder) {
        log.debug("create(environmentCreate={})", env);
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe: bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "environment/create";
        }

        Long id = environmentFacade.createEnvironment(env);

        redirectAttributes.addFlashAttribute("alert_success", "Environment " + env.getName() + " was created successfully");
        return "redirect:" + urisBuilder.path("/environment/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

}


