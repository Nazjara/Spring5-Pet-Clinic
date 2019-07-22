package com.nazjara.service.map;

import com.nazjara.model.Owner;
import com.nazjara.model.Pet;
import com.nazjara.model.PetType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OwnerMapServiceTest {

    @InjectMocks
    private OwnerMapService ownerMapService;

    @Mock
    private PetTypeMapService petTypeService;

    @Mock
    private PetMapService petService;

    private Owner owner;

    @BeforeEach
    public void setUp() {
        owner = Owner.builder().id(1L).pets(new HashSet<>()).lastName("lastName").build();

        Mockito.lenient().when(petService.save(any(Pet.class))).thenCallRealMethod();
        Mockito.lenient().when(petTypeService.save(any(PetType.class))).thenCallRealMethod();

        ownerMapService.save(owner);
    }

    @AfterEach
    public void tearDown() {
        ownerMapService.delete(owner);
    }

    @Test
    void testFindAll() {
        assertEquals(1, ownerMapService.findAll().size());
    }

    @Test
    void testDeleteById() {
        ownerMapService.deleteById(owner.getId());

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void testDelete() {
        ownerMapService.delete(owner);

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void testSave() {
        ownerMapService.save(Owner.builder().id(2L).pets(new HashSet<>()).build());

        assertEquals(2, ownerMapService.findAll().size());
    }

    @Test
    void testFindById() {
        assertSame(ownerMapService.findById(1L), owner);
    }

    @Test
    void testFindByLastName() {
        assertSame(ownerMapService.findByLastName("lastName"), owner);
    }
}