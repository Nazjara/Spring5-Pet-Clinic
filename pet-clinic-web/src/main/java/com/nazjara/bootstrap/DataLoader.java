package com.nazjara.bootstrap;

import com.nazjara.model.Owner;
import com.nazjara.model.PetType;
import com.nazjara.model.Vet;
import com.nazjara.service.OwnerService;
import com.nazjara.service.PetTypeService;
import com.nazjara.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dog = new PetType();
        dog.setName("Dog");

        PetType cat = new PetType();
        cat.setName("Cat");

        petTypeService.save(dog);
        petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("Owner's 1 firstname");
        owner1.setLastName("Owner's 1 lastname");

        Owner owner2 = new Owner();
        owner2.setFirstName("Owner's 2 firstname");
        owner2.setLastName("Owner's 2 lastname");

        ownerService.save(owner1);
        ownerService.save(owner2);

        System.out.println("Owners are loaded");

        Vet vet1 = new Vet();
        vet1.setFirstName("Vet's 1 firstname");
        vet1.setLastName("Vet's 1 lastname");

        Vet vet2 = new Vet();
        vet2.setFirstName("Vet's 2 firstname");
        vet2.setLastName("Vet's 2 lastname");

        vetService.save(vet1);
        vetService.save(vet2);

        System.out.println("Vets are loaded");
    }
}