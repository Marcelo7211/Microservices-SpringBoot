package com.ead.course.service.impl;

import com.ead.course.models.Course;
import com.ead.course.models.Lesson;
import com.ead.course.models.Modulo;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuloRepository;
import com.ead.course.service.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void delete(Course course) {
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
}
