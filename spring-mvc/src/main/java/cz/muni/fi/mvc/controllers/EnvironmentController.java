package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.EnvironmentListDTO;
import cz.muni.fi.facades.EnvironmentFacade;
import cz.muni.fi.mvc.validators.EnvironmentCreateDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Ondrej Slimak
 */

@Controller
@RequestMapping("/environment")
public class EnvironmentController {

    @Autowired
    private final EnvironmentFacade environmentFacade;

    public EnvironmentController(EnvironmentFacade environmentFacade) {
        this.environmentFacade = environmentFacade;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("environments", environmentFacade.getAllEnvironment());
        return "environment/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String listFreeAgents(@PathVariable long id, Model model) {
        model.addAttribute("environment", environmentFacade.getEnvironmentById(id));
        return "environment/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
                         Model model,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        EnvironmentDTO env = environmentFacade.getEnvironmentById(id);
        environmentFacade.deleteEnvironment(id);

        redirectAttributes.addFlashAttribute("alert_success", "Environment " + env.getName() + " was successfully deleted");
        return "redirect:" + uriBuilder.path("/environment/list").toUriString();
    }


    @ModelAttribute("environments")
    public List<EnvironmentListDTO> environments() {
        return environmentFacade.getAllEnvironment();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof EnvironmentCreateDTO) {
            binder.addValidators(new EnvironmentCreateDtoValidator());
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("createPlayer") EnvironmentCreateDTO env,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder urisBuilder) {

        if (bindingResult.hasErrors()) {
            for (FieldError error: bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }
            return "environment/create";
        }

        Long id = environmentFacade.createEnvironment(env);

        redirectAttributes.addFlashAttribute("alert_success", "Environment " + env.getName() + " was created successfully");
        return "redirect:" + urisBuilder.path("/environment/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

}


