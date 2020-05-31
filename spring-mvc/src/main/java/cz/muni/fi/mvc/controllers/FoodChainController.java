package cz.muni.fi.mvc.controllers;

import cz.muni.fi.dto.*;
import cz.muni.fi.facades.AnimalFacade;
import cz.muni.fi.facades.FoodChainFacade;
import cz.muni.fi.mvc.validators.FoodChainCreateDtoValidator;
import cz.muni.fi.mvc.validators.FoodChainUpdateDtoValidator;
import dao.entities.AnimalInFoodChain;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof FoodChainCreateDTO) {
            binder.addValidators(new FoodChainCreateDtoValidator());
        }
        if (binder.getTarget() instanceof FoodChainDTO) {
            binder.addValidators(new FoodChainUpdateDtoValidator());
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        log.info("list()");
        model.addAttribute("foodChains", foodChainFacade.getAllFoodChains());
        return "foodChain/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(UriComponentsBuilder uriBuilder) {
        log.info("create()");
        FoodChainCreateDTO foodChainDTO = new FoodChainCreateDTO();
        foodChainDTO.setAnimalsInFoodChain(new ArrayList<>());
        Long foodChainId = foodChainFacade.createFoodChain(foodChainDTO);
        return "redirect:" + uriBuilder.path("/foodChain/detail/{id}").buildAndExpand(foodChainId).encode().toUriString();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        log.info("detail({})", id);
        List<AnimalInFoodChainDTO> animalInFoodChainDTOS = foodChainFacade.getFoodChainById(id).getAnimalsInFoodChain();
        List<AnimalListDTO> animalNotInFoodChainDTOS = new ArrayList<>();

        boolean contained = false;
        for (AnimalListDTO a : animalFacade.getAllAnimals()) {
            for (AnimalInFoodChainDTO dto : animalInFoodChainDTOS) {
                if (Objects.equals(a.getId(), dto.getAnimal().getId())) {
                    contained = true;
                    break;
                }
            }

            if (!contained){
                animalNotInFoodChainDTOS.add(a);
            }
            contained = false;
        }

        model.addAttribute("foodChain", foodChainFacade.getFoodChainById(id));
        model.addAttribute("animalsNotInFoodChain", animalNotInFoodChainDTOS);
        model.addAttribute("animalsInFoodChain", animalInFoodChainDTOS);
        return "foodChain/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        FoodChainDTO foodChainDTO = foodChainFacade.getFoodChainById(id);
        foodChainFacade.deleteFoodChain(id);

        redirectAttributes.addFlashAttribute("alert_success", "FoodChain with id" + foodChainDTO.getId() + " was successfully deleted");
        return "redirect:" + uriBuilder.path("/foodChain").toUriString();
    }

    @RequestMapping(value = "/detail/{id1}/removeAnimal/{id2}", method = RequestMethod.POST)
    public String removeAnimal(@PathVariable long id1,@PathVariable long id2,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        log.info("removeAnimal(foodChainId{}, animalId{})", id1, id2);

        FoodChainDTO foodChainDTO = foodChainFacade.getFoodChainById(id1);
        AnimalInFoodChainDTO animalInFoodChainDTO = null;
        for(AnimalInFoodChainDTO a : foodChainDTO.getAnimalsInFoodChain()){
            if (a.getId().equals(id2)){
                animalInFoodChainDTO = a;
            }
        }

        foodChainFacade.removeAnimal(animalInFoodChainDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Animal " + animalInFoodChainDTO.getAnimal().getName() +
                " was successfully removed from FoodChain");
        return "redirect:" + uriBuilder.path("/foodChain/detail/{id1}").buildAndExpand(id1).encode().toUriString();
    }

    @RequestMapping(value = "/detail/{id1}/addAnimal", method = RequestMethod.POST)
    public String addAnimalFormHandler(
            @PathVariable long id1,
            @RequestParam("animalId") Long id2,
            Model model,
            UriComponentsBuilder uriBuilder,
            HttpServletRequest request) {
        log.info("addAnimalFormHandler(foodChainId{}, animalId{})", id1, id2);
        if (id2 == 0) {
            return "redirect:" + uriBuilder.path("/foodChain/detail/{id1}").encode().toUriString();
        }
        return addAnimal(id1, id2, model, uriBuilder, request);
    }

    @RequestMapping(value = "/detail/{id1}/addAnimal/{id2}", method = RequestMethod.GET)
    public String addAnimal(@PathVariable Long id1,
                            @PathVariable Long id2,
                            Model model,
                            UriComponentsBuilder uriBuilder,
                            HttpServletRequest request) {

        log.info("addAnimal({})", id2);
        model.addAttribute("selectedAnimalId", id2);

        AnimalDTO animalDTO = animalFacade.getAnimalById(id2);

        if (animalDTO == null) {
            log.info("animal({}) not found", id2);
            return "redirect:" + uriBuilder.path("/foodChain/detail/{id1}").encode().toUriString();
        }

        if (request.getParameter("beginning") != null) {
            log.info("addAnimalToBeginning({})", id2);
            foodChainFacade.addAnimalToBeginning(animalDTO, id1);

        } else if (request.getParameter("end") != null) {
            log.info("addAnimalToEnd({})", id2);
            foodChainFacade.addAnimalToEnd(animalDTO, id1);
        }


        return "redirect:" + uriBuilder.path("/foodChain/detail/{id1}").buildAndExpand(id1).encode().toUriString();
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
        log.info("getFoodChainsByAnimal({})", id);
        model.addAttribute("selectedAnimalId", id);
        AnimalDTO animalDTO = animalFacade.getAnimalById(id);
        if (animalDTO == null) {
            return "redirect:" + uriBuilder.path("/foodChain/").encode().toUriString();
        }

        model.addAttribute("foodChains", foodChainFacade.getFoodChainsWithAnimal(animalDTO.getId()));
        return "foodChain/list";
    }
    @ModelAttribute("animals")
    public List<AnimalListDTO> animals() {
        return animalFacade.getAllAnimals();
    }
}
