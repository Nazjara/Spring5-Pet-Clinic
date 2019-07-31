package com.nazjara.controller;

import com.nazjara.model.Owner;
import com.nazjara.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {

    @InjectMocks
    private OwnerController ownerController;

    @Mock
    private OwnerService ownerService;

    @Mock
    private Owner owner;

    private Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
        owners = Set.of(Owner.builder().id(1L).build(), Owner.builder().id(2L).build());

        Mockito.lenient().when(ownerService.findAll()).thenReturn(owners);
        Mockito.lenient().when(ownerService.findById(1L)).thenReturn(owner);
        Mockito.lenient().when(owner.getId()).thenReturn(1L);
    }

    @Test
    void testDisplayOwner() throws Exception {
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", owner));

        verify(ownerService).findById(1L);
    }

    @Test
    void testFindOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyZeroInteractions(ownerService);
    }

    @Test
    void testFindFormReturnsZero() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeDoesNotExist("owners"));

        verify(ownerService).findAllByLastNameLike(anyString());
    }

    @Test
    void testFindFormReturnsOne() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(List.of(owner));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeDoesNotExist("owners"));

        verify(ownerService).findAllByLastNameLike(anyString());
    }

    @Test
    void testFindFormReturnsMultiple() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(owners));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("owners", hasSize(2)));

        verify(ownerService).findAllByLastNameLike(anyString());
    }
}