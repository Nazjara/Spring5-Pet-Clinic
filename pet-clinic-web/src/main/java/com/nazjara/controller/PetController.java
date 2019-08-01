package com.nazjara.controller;

import com.nazjara.model.Owner;
import com.nazjara.model.Pet;
import com.nazjara.model.PetType;
import com.nazjara.service.OwnerService;
import com.nazjara.service.PetService;
import com.nazjara.service.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private PetTypeService petTypeService;
    private OwnerService ownerService;
    private PetService petService;

    @Autowired
    public PetController(PetTypeService petTypeService, OwnerService ownerService, PetService petService) {
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @ModelAttribute("petTypes")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model) {
        model.addAttribute("pet", Pet.builder().owner(owner).build());
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model) {
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPetByName(pet.getName()) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            pet.setOwner(owner);
            petService.save(pet);
            return String.format("redirect:/owners/%s", owner.getId());
        }
    }

    @GetMapping("/pets/{id}/edit")
    public String initUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("pet", petService.findById(id));
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/{id}/edit")
    public String processUpdateForm(@PathVariable Long id, Owner owner, @Valid Pet pet, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            pet.setId(id);
            pet.setOwner(owner);
            petService.save(pet);
            return String.format("redirect:/owners/%s", owner.getId());
        }
    }
}