package com.ead.course.service;

import com.ead.course.dto.ModuloDto;
import com.ead.course.models.Modulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ModuloService {

    public void delete (UUID moduloId);

    public ModuloDto save(ModuloDto moduloDto);

    public ModuloDto update(UUID moduloId, ModuloDto moduloDto);

    public Page<Modulo> findAllPageable(Specification<Modulo> spec, Pageable pageable);

    public Modulo findById(UUID moduloId);
}
