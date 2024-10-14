package com.ead.course.service.impl;

import com.ead.course.dto.ModuloDto;
import com.ead.course.models.Lesson;
import com.ead.course.models.Modulo;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuloRepository;
import com.ead.course.service.ModuloService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuloImpl implements ModuloService {

    @Autowired
    private ModuloRepository repository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(UUID moduloId) {

        Modulo modulo = findById(moduloId);

        List<Lesson> lessonList = lessonRepository.findAllLessonsIntoModule(modulo.getModuleId());
        if (!lessonList.isEmpty()){
            lessonRepository.deleteAll(lessonList);
        }
        repository.deleteById(modulo.getModuleId());

    }

    @Override
    public ModuloDto save(ModuloDto moduloDto) {
        var moduloModel = new Modulo();

        BeanUtils.copyProperties(moduloDto, moduloModel);

        moduloModel.getCourse().setCourseId(moduloDto.getCourseId());
        moduloModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));

        BeanUtils.copyProperties(repository.save(moduloModel), moduloDto);

        return moduloDto;
    }

    @Override
    public ModuloDto update(UUID moduloId, ModuloDto moduloDto) {

        Modulo moduloSaved = findById(moduloId);

        var moduloModel = new Modulo();

        BeanUtils.copyProperties(moduloDto, moduloModel);
        moduloModel.getCourse().setCourseId(moduloDto.getCourseId());

        moduloModel.setModuleId(moduloSaved.getModuleId());
        moduloModel.setCreationDate(moduloSaved.getCreationDate());
        moduloModel.getCourse().setCourseId(moduloDto.getCourseId());

        BeanUtils.copyProperties(repository.save(moduloModel), moduloDto);

        return moduloDto;
    }

    @Override
    public Page<Modulo> findAllPageable(Specification<Modulo> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public Modulo findById(UUID moduloId) {
        Optional<Modulo> moduloModel = repository.findById(moduloId);

        if(moduloModel.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource or Object not found");

        return moduloModel.get();
    }
}
