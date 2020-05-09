package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.AuthenticateUserDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.facades.UserFacade;
import cz.muni.fi.mvc.validators.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author Ondrej Slimak
 */
@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserFacade userFacade;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof UserDTO) {
            binder.addValidators(new AuthValidator());
        }
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("authenticatedUser") != null) {
            return "redirect:/";
        }

        model.addAttribute("userLogin", new AuthenticateUserDTO());
        return "/auth/login";
    }

    @RequestMapping(value = "auth/login", method = RequestMethod.POST)
    public String postLogin(@Valid @ModelAttribute("userLogin") AuthenticateUserDTO userLogin,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            UriComponentsBuilder uriBuilder,
                            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }

            model.addAttribute("userLogin", new AuthenticateUserDTO());
            return "auth/login";
        }

        UserDTO found = userFacade.getUserByEmail(userLogin.getEmail());

        if (found == null) { // TODO: add ' || !userFacade.authenticate(userLogin)'
            redirectAttributes.addFlashAttribute("alert_warning", "Login with email " + userLogin.getEmail()
                    + " has failed. Wrong password?");
            return "redirect:" + uriBuilder.path("/auth/login").toUriString();
        }

        request.getSession().setAttribute("authenticatedUser", found);
        redirectAttributes.addFlashAttribute("alert_success", "Logged in successfully");

        return "redirect:" + uriBuilder.path("/").toUriString();
    }

    @RequestMapping(value = "/auth/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request) {
        request.getSession().removeAttribute("authenticatedUser");
        return "redirect:/";
    }


}

