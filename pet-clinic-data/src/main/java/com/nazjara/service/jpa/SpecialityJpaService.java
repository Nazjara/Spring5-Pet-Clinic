package com.nazjara.service.jpa;

import com.nazjara.model.Specialty;
import com.nazjara.repository.SpecialityRepository;
import com.nazjara.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class SpecialityJpaService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    @Autowired
    public SpecialityJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Specialty> findAll() {
        Set<Specialty> specialities = new HashSet<>();
        specialityRepository.findAll().forEach(specialities::add);

        return specialities;
    }

    @Override
    public Specialty findById(Long aLong) {
        return specialityRepository.findById(aLong).orElse(null);
    }

    @Override
    public Specialty save(Specialty object) {
        return specialityRepository.save(object);
    }

    @Override
    public void delete(Specialty object) {
        specialityRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        specialityRepository.deleteById(aLong);
    }
}