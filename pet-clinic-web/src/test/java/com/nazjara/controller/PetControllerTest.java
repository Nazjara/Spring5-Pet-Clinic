package com.nazjara.controller;

import com.nazjara.model.Owner;
import com.nazjara.model.Pet;
import com.nazjara.model.PetType;
import com.nazjara.service.OwnerService;
import com.nazjara.service.PetService;
import com.nazjara.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

    @InjectMocks
    private PetController petController;

    @Mock
    private PetTypeService petTypeService;

    @Mock
    private OwnerService ownerService;

    @Mock
    private PetService petService;

    @Mock
    private Owner owner;

    @Mock
    private Pet pet;

    @Mock
    private BindingResult result;

    @Mock
    private Model model;

    private Set<PetType> petTypes;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        petTypes = Set.of(PetType.builder().id(1L).build(), PetType.builder().id(2L).build());

        Mockito.lenient().when(petTypeService.findAll()).thenReturn(petTypes);
        Mockito.lenient().when(ownerService.findById(1L)).thenReturn(owner);
        Mockito.lenient().when(petService.findById(1L)).thenReturn(pet);
        Mockito.lenient().when(owner.getId()).thenReturn(1L);
    }

    @Test
    public void testPopulatePetTypes() {
        assertSame(petTypes, petController.populatePetTypes());

        verify(petTypeService).findAll();
    }

    @Test
    public void testFindOwner() {
        assertSame(owner, petController.findOwner(1L));

        verify(ownerService).findById(1L);
    }

    @Test
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    public void testProcessCreationForm() throws Exception {
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(petService).save(any(Pet.class));
    }

    @Test
    public void testProcessCreationFormFailed() throws Exception {
        when(pet.getName()).thenReturn("name");
        when(pet.isNew()).thenReturn(true);
        when(owner.getPetByName("name")).thenReturn(pet);
        when(result.hasErrors()).thenReturn(true);

        assertEquals("pets/createOrUpdatePetForm", petController.processCreationForm(owner, pet, result, model));

        verify(pet, times(2)).getName();
        verify(pet).isNew();
        verify(result).rejectValue("name", "duplicate", "already exists");
        verify(result).hasErrors();
        verify(model).addAttribute("pet", pet);
    }

    @Test
    public void testInitUpdateForm() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"));

        verify(petService).findById(1L);
    }

    @Test
    public void testProcessUpdateForm() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(petService).save(any(Pet.class));
    }

    @Test
    public void testProcessUpdateFormFailed() throws Exception {
        when(result.hasErrors()).thenReturn(true);

        assertEquals("pets/createOrUpdatePetForm", petController.processUpdateForm(1L, owner, pet, result, model));

        verify(result).hasErrors();
        verify(model).addAttribute("pet", pet);
    }
}