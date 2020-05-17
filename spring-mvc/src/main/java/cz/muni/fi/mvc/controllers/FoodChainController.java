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
        List<AnimalInFoodChainDTO> animalInFoodChainDTOS = foodChainFacade.getFoodChainById(id).getAnimalsInFoodChain();
        model.addAttribute("foodChain", foodChainFacade.getFoodChainById(id));
        List<AnimalListDTO> animalNotInFoodChainDTOS = new ArrayList<>();
        boolean contained = true;

        for (AnimalListDTO a : animalFacade.getAllAnimals()){
            for(AnimalInFoodChainDTO dto : animalInFoodChainDTOS){
                if (Objects.equals(a.getId(), dto.getAnimal().getId())){
                    contained = true;
                    break;
                }
                else{
                    contained = false;
                }
            }
            if (!contained){
                contained = true;
                animalNotInFoodChainDTOS.add(a);
            }
        }
        model.addAttribute("animalsNotInFoodChain", animalNotInFoodChainDTOS);
        model.addAttribute("animalsInFoodChain", animalInFoodChainDTOS);
        return "foodChain/detail";
    }

    @RequestMapping(value = "/detail/{id1}/removeAnimal/{id2}", method = RequestMethod.POST)
    public String removeAnimal(@PathVariable long id1,@PathVariable long id2,
                         Model model,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {

        FoodChainDTO foodChainDTO = foodChainFacade.getFoodChainById(id1);
        AnimalInFoodChainDTO animalInFoodChainDTO = null;
        for(AnimalInFoodChainDTO a : foodChainDTO.getAnimalsInFoodChain()){
            if (a.getId().equals(id2)){
                animalInFoodChainDTO = a;
            }
        }
        log.warn(animalInFoodChainDTO.getAnimal().getName());
        foodChainFacade.removeAnimal(animalInFoodChainDTO);

        redirectAttributes.addFlashAttribute("alert_success", "Animal" + animalInFoodChainDTO.getAnimal().getName() +
                " was successfully removed from FoodChain");
        return "redirect:" + uriBuilder.path("/foodChain/detail/{id1}").buildAndExpand(id1).encode().toUriString();
    }

    @RequestMapping(value = "/detail/{id1}/addAnimal", method = RequestMethod.POST)
    public String addAnimalFormHandler(@PathVariable long id1, @RequestParam("animalId") Long id2, UriComponentsBuilder uriBuilder) {
        if (id2 == 0) {
            return "redirect:" + uriBuilder.path("/foodChain/detail/{id1}").encode().toUriString();
        }
        return "redirect:" + uriBuilder.path("/foodChain/detail/{id1}/addAnimal/{id2}").buildAndExpand(id1, id2).encode().toUriString();
    }

    @RequestMapping(value = "/detail/{id1}/addAnimal/{id2}", method = RequestMethod.GET)
    public String addAnimal(@PathVariable Long id1, @PathVariable Long id2, Model model, UriComponentsBuilder uriBuilder) {
        log.debug("addAnimal({})", id2);
        model.addAttribute("selectedAnimalId", id2);

        AnimalDTO animalDTO = animalFacade.getAnimalById(id2);

        if (animalDTO == null) {
            return "redirect:" + uriBuilder.path("/foodChain/detail/{id1}").encode().toUriString();
        }
        foodChainFacade.addAnimalToBeginning(animalDTO,id1);

        return "foodChain/list";
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("create()");
        model.addAttribute("foodChainCreate", new FoodChainCreateDTO());
        model.addAttribute("animals", animals());
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

        List<AnimalInFoodChainDTO> animalInFoodChain = new ArrayList<>();
        List<Long> animalInFoodChainIds = foodChainCreateDTO.getAnimalInFoodChainIds();
        for (int i = 0; i < animalInFoodChainIds.size(); i++) {
            Long id = animalInFoodChainIds.get(i);
            AnimalDTO animal = animalFacade.getAnimalById(id);
            AnimalInFoodChainDTO aaa = new AnimalInFoodChainDTO();

            aaa.setIndexInFoodChain(i+1);
            aaa.setAnimal(animal);
            //aaa.setFoodChain();
            animalInFoodChain.add(aaa);
        }

        foodChainCreateDTO.setAnimalsInFoodChain(animalInFoodChain);
        log.warn("size: " + foodChainCreateDTO.getAnimalsInFoodChain().size());
        Long id = foodChainFacade.createFoodChain(foodChainCreateDTO);

        redirectAttributes.addFlashAttribute("alert_success", "FoodChain " + id + " was created successfully");
        return "redirect:" + urisBuilder.path("/foodChain/detail/{id}").buildAndExpand(id).encode().toUriString();
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
        log.debug("getFoodChainsByAnimal({})", id);
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
        log.debug("animals()");
        return animalFacade.getAllAnimals();
    }
}
