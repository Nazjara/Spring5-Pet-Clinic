package com.nazjara.service.map;

import com.nazjara.model.Specialty;
import com.nazjara.service.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"map", "default"})
public class SpecialityMapService extends AbstractMapService<Specialty, Long> implements SpecialityService {

    @Override
    public Set<Specialty> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Specialty specialty) {
        super.delete(specialty);
    }

    @Override
    public Specialty save(Specialty specialty) {
        return super.save(specialty);
    }

    @Override
    public Specialty findById(Long id) {
        return super.findById(id);
    }
}