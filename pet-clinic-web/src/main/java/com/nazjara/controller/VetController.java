package com.nazjara.controller;

import com.nazjara.model.Vet;
import com.nazjara.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetController {

    private final VetService vetService;

    @Autowired
    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("/vets")
    public String listVets(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets/vetsList";
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> getVets() {
        return vetService.findAll();
    }
}
