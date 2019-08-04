package com.nazjara.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

    @Builder
    public Pet(Long id, PetType petType, Owner owner, LocalDate birthDate, String name, Set<Visit> visits) {
        super(id);
        this.petType = petType;
        this.owner = owner;
        this.birthDate = birthDate;
        this.name = name;
        this.visits = visits == null ? new HashSet<>() : visits;
    }

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate birthDate;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits = new HashSet<>();
}
