package com.nazjara.controller;

import com.nazjara.model.Owner;
import com.nazjara.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    private OwnerController ownerController;

    @Mock
    private OwnerService ownerService;

    private Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
        owners = Set.of(Owner.builder().id(1L).build(), Owner.builder().id(2L).build());

        when(ownerService.findAll()).thenReturn(owners);
    }

    @Test
    void testListOwners() throws Exception {
        mockMvc.perform(get("/owners"))
                .andExpect(status().is(200))
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));

        verify(ownerService).findAll();
    }
}