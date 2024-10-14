package com.ead.course.service;

import com.ead.course.dto.CourseDto;
import com.ead.course.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    public void delete(UUID courseId);

    public CourseDto save(CourseDto course);

    public Course findById (UUID courseId);

    public Page<Course> findAllPageable(Specification<Course> spec, Pageable pageable);

    public CourseDto update(UUID courseId, CourseDto courseDto);
}
