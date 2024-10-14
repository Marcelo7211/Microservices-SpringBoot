package com.ead.course.service.impl;

import com.ead.course.dto.LessonDto;
import com.ead.course.models.Course;
import com.ead.course.models.Lesson;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
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
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class LessonImpl implements LessonService {

    @Autowired
    private LessonRepository repository;

    @Override
    public LessonDto save(LessonDto lessonDto) {
        var lessonModel = new Lesson();

        BeanUtils.copyProperties(lessonDto, lessonModel);
        lessonModel.getModulo().setModuleId(lessonDto.getModuloId());
        lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));

        BeanUtils.copyProperties(repository.save(lessonModel), lessonDto);

        return lessonDto;
    }

    @Override
    public LessonDto update(UUID lessonId, LessonDto lessonDto) {

        Lesson lessonSaved = findById(lessonId);

        var lessonModel = new Lesson();

        BeanUtils.copyProperties(lessonDto, lessonModel);
        lessonModel.getModulo().setModuleId(lessonDto.getModuloId());
        lessonModel.setCreationDate(lessonSaved.getCreationDate());

        BeanUtils.copyProperties(repository.save(lessonModel), lessonDto);

        return lessonDto;

    }

    @Override
    public void delete(UUID lessonId) {
        findById(lessonId);
        repository.deleteById(lessonId);
    }

    @Override
    public Page<Lesson> findAllPageable(Specification<Lesson> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public Lesson findById(UUID lessonId) {
        Optional<Lesson> lessonModel = repository.findById(lessonId);

        if(lessonModel.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource or Object not found");

        return lessonModel.get();
    }
}
