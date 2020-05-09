package cz.muni.fi.mvc.controllers;

import cz.muni.fi.facades.AnimalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Milos Ferencik
 */

@Controller
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private AnimalFacade animalFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        System.out.println(request.getSession().getId());
        model.addAttribute("animals", animalFacade.getAllAnimals());
        return "animal/list";
    }
}
