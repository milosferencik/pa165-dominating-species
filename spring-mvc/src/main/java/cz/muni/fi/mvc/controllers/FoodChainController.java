package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.EnvironmentDTO;
import cz.muni.fi.dto.FoodChainDTO;
import cz.muni.fi.facades.FoodChainFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Petr Kostka
 */
@Controller
@RequestMapping("/foodChain")
public class FoodChainController {

    @Autowired
    private FoodChainFacade foodChainFacade;

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
}
