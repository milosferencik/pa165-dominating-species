package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.EnvironmentCreateDTO;
import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.UserCreateDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.facades.UserFacade;
import cz.muni.fi.mvc.validators.EnvironmentCreateDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @Katarina Matusova
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userFacade.getAllUsers());
        return "user/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("user", userFacade.getUserById(id));
        return "user/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
                         Model model,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        UserDTO user = userFacade.getUserById(id);
        try {
            userFacade.deleteUser(id);
            redirectAttributes.addFlashAttribute("alert_success", "User \"" + user.getEmail() + "\" was successfully deleted.");
        } catch (Exception ex){
            redirectAttributes.addFlashAttribute("alert_danger", "User \"" + user.getEmail() + "\" cannot be deleted.");
        }
        return "redirect:" + uriBuilder.path("/user/list").toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("createUser", new UserCreateDTO());
        return "user/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("createUser") UserCreateDTO user,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder urisBuilder) {

        if (bindingResult.hasErrors()) {
            for (FieldError error: bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }
            return "user/create";
        }

        Long id = userFacade.createUser(user,user.getPassword());

        redirectAttributes.addFlashAttribute("alert_success", "User " + user.getEmail() + " was created successfully");
        return "redirect:" + urisBuilder.path("/user/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

}
