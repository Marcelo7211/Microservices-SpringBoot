package com.ead.course.service.impl;

import com.ead.course.dto.CourseDto;
import com.ead.course.models.Course;
import com.ead.course.models.Lesson;
import com.ead.course.models.Modulo;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuloRepository;
import com.ead.course.service.CourseService;
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
public class CourseImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(UUID courseId) {

        Course course = this.findById(courseId);

        List<Modulo> moduleModelList = moduloRepository.findAllModulosIntoCourse(course.getCourseId());

        if(!moduleModelList.isEmpty()) {
            for(Modulo modulo : moduleModelList){
                List<Lesson> lessonList = lessonRepository.findAllLessonsIntoModule(modulo.getModuleId());
                if(!lessonList.isEmpty()){
                    lessonRepository.deleteAll(lessonList);
                }
            }
            moduloRepository.deleteAll(moduleModelList);
        }

        repository.deleteById(course.getCourseId());
    }

    @Override
    public CourseDto save(CourseDto courseDto) {

        var courseModel = new Course();

        BeanUtils.copyProperties(courseDto, courseModel);

        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdadeDate(LocalDateTime.now(ZoneId.of("UTC")));

        BeanUtils.copyProperties(repository.save(courseModel), courseDto);

        return courseDto;
    }

    @Override
    public Course findById(UUID courseId) {

        Optional<Course> courseModel = repository.findById(courseId);

        if(courseModel.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource or Object not found");

        return courseModel.get();
    }

    @Override
    public Page<Course> findAllPageable(Specification<Course> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public CourseDto update(UUID courseId, CourseDto courseDto) {
         Course courseSaved = findById(courseId);

         var courseModel = new Course();
         BeanUtils.copyProperties(courseDto, courseModel);

         courseModel.setCourseId(courseSaved.getCourseId());
         courseModel.setCreationDate(courseSaved.getCreationDate());
         courseModel.setLastUpdadeDate(LocalDateTime.now(ZoneId.of("UTC")));

         repository.save(courseModel);

         return courseDto ;
    }
}
