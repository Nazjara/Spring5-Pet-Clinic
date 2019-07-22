package com.nazjara.service.jpa;

import com.nazjara.model.Owner;
import com.nazjara.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {

    @InjectMocks
    private OwnerJpaService ownerJpaService;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private Owner owner;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(ownerRepository.findByLastName("lastName")).thenReturn(owner);
        Mockito.lenient().when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.lenient().when(ownerRepository.findAll()).thenReturn(Set.of(owner));
        Mockito.lenient().when(ownerRepository.save(any(Owner.class))).thenReturn(owner);
        Mockito.lenient().doNothing().when(ownerRepository).delete(any(Owner.class));
        Mockito.lenient().doNothing().when(ownerRepository).deleteById(anyLong());
    }

    @Test
    void testFindByLastName() {
        assertSame(owner, ownerJpaService.findByLastName("lastName"));

        verify(ownerRepository).findByLastName("lastName");
    }

    @Test
    void testFindAll() {
        Set<Owner> owners = ownerJpaService.findAll();

        assertEquals(1, owners.size());
        assertSame(owner, owners.iterator().next());

        verify(ownerRepository).findAll();
    }

    @Test
    void testFindById() {
        assertSame(owner, ownerJpaService.findById(1L));

        verify(ownerRepository).findById(1L);
    }

    @Test
    void testSave() {
        assertSame(owner, ownerJpaService.save(owner));

        verify(ownerRepository).save(owner);
    }

    @Test
    void testDelete() {
        ownerJpaService.delete(owner);

        verify(ownerRepository).delete(owner);
    }

    @Test
    void testDeleteById() {
        ownerJpaService.deleteById(1L);

        verify(ownerRepository).deleteById(1L);
    }
}