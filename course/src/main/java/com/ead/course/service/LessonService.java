package com.ead.course.service;

import com.ead.course.dto.LessonDto;
import com.ead.course.models.Course;
import com.ead.course.models.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface LessonService {

    LessonDto save(LessonDto lessonDto);

    LessonDto update (UUID lessonId, LessonDto lessonDto);

    void delete(UUID courseId);

    Page<Lesson> findAllPageable(Specification<Lesson> spec, Pageable pageable);

    Lesson findById(UUID lessonId);
}
