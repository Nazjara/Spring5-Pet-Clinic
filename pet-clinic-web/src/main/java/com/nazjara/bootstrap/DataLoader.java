package com.nazjara.bootstrap;

import com.nazjara.model.Owner;
import com.nazjara.model.Vet;
import com.nazjara.service.OwnerService;
import com.nazjara.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Owner's 1 firstname");
        owner1.setLastName("Owner's 1 lastname1");

        Owner owner2 = new Owner();
        owner1.setId(2L);
        owner1.setFirstName("Owner's 2 firstname");
        owner1.setLastName("Owner's 2 lastname");

        ownerService.save(owner1);
        ownerService.save(owner2);

        System.out.println("Owners are loaded");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Vet's 1 firstname");
        vet1.setLastName("Vet's 1 lastname");

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Vet's 2 firstname");
        vet2.setLastName("Vet's 2 lastname");

        vetService.save(vet1);
        vetService.save(vet2);

        System.out.println("Vets are loaded");
    }
}