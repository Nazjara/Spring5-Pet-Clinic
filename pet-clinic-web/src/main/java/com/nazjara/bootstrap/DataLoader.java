package com.nazjara.bootstrap;

import com.nazjara.model.*;
import com.nazjara.service.OwnerService;
import com.nazjara.service.PetTypeService;
import com.nazjara.service.SpecialityService;
import com.nazjara.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (petTypeService.findAll().size() == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");

        PetType cat = new PetType();
        cat.setName("Cat");

        dog = petTypeService.save(dog);
        cat = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");

        radiology = specialityService.save(radiology);
        surgery = specialityService.save(surgery);
        dentistry = specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Owner's 1 firstname");
        owner1.setLastName("Owner's 1 lastname");
        owner1.setAddress("Address 1");
        owner1.setCity("City 1");
        owner1.setTelephone("Phone 1");

        Pet pet1 = new Pet();
        pet1.setPetType(dog);
        pet1.setOwner(owner1);
        pet1.setBirthDate(LocalDate.now());
        pet1.setName("Dog");

        owner1.getPets().add(pet1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Owner's 2 firstname");
        owner2.setLastName("Owner's 2 lastname");
        owner1.setAddress("Address 2");
        owner1.setCity("City 2");
        owner1.setTelephone("Phone 2");

        Pet pet2 = new Pet();
        pet2.setPetType(cat);
        pet2.setOwner(owner2);
        pet2.setBirthDate(LocalDate.now());
        pet2.setName("Cat");

        owner2.getPets().add(pet2);

        ownerService.save(owner1);
        ownerService.save(owner2);

        System.out.println("Owners are loaded");

        Vet vet1 = new Vet();
        vet1.setFirstName("Vet's 1 firstname");
        vet1.setLastName("Vet's 1 lastname");
        vet1.getSpecialities().add(radiology);

        Vet vet2 = new Vet();
        vet2.setFirstName("Vet's 2 firstname");
        vet2.setLastName("Vet's 2 lastname");
        vet2.getSpecialities().add(surgery);

        Vet vet3 = new Vet();
        vet3.setFirstName("Vet's 3 firstname");
        vet3.setLastName("Vet's 3 lastname");
        vet3.getSpecialities().add(dentistry);

        vetService.save(vet1);
        vetService.save(vet2);
        vetService.save(vet3);

        System.out.println("Vets are loaded");
    }
}