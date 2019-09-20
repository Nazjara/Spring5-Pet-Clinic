package com.nazjara.controller;

import com.nazjara.model.Pet;
import com.nazjara.model.Visit;
import com.nazjara.service.PetService;
import com.nazjara.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {

    @InjectMocks
    private VisitController visitController;

    @Mock
    private VisitService visitService;

    @Mock
    private PetService petService;

    @Mock
    private Pet pet;

    @Mock
    private Visit visit;

    @Mock
    private BindingResult result;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

        Mockito.lenient().when(petService.findById(1L)).thenReturn(pet);
        Mockito.lenient().when(visitService.save(any(Visit.class))).thenReturn(visit);
    }

    @Test
    public void testLoadPet() {
        assertSame(pet, visitController.loadPet(1L));

        verify(petService).findById(1L);
    }

    @Test
    public void testInitCreateForm() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("visit/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"));
    }

    @Test
    public void testProcessUpdateForm() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(visitService).save(any(Visit.class));
    }

    @Test
    public void testProcessCreateFormFailed() throws Exception {
        when(result.hasErrors()).thenReturn(true);

        assertEquals("visit/createOrUpdateVisitForm", visitController.processCreateForm(1L, pet, visit, result));

        verify(result).hasErrors();
    }
}