package com.nazjara.controller;

import com.nazjara.model.Pet;
import com.nazjara.model.Visit;
import com.nazjara.service.PetService;
import com.nazjara.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;

    @Autowired
    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text));
            }
        });
    }

    @ModelAttribute("pet")
    public Pet loadPet(@PathVariable("petId") Long petId) {
        return petService.findById(petId);
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(Pet pet, Model model) {
        model.addAttribute("pet", pet);
        model.addAttribute(Visit.builder().build());

        return "visit/createOrUpdateVisitForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@PathVariable Long ownerId, Pet pet, @Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return "visit/createOrUpdateVisitForm";
        } else {
            visit.setPet(pet);
            visitService.save(visit);
            return String.format("redirect:/owners/%s", ownerId);
        }
    }

}