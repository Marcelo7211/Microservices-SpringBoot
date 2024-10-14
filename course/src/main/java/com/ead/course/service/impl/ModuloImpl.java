package com.ead.course.service.impl;

import com.ead.course.models.Lesson;
import com.ead.course.models.Modulo;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuloRepository;
import com.ead.course.service.ModuloService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuloImpl implements ModuloService {

    @Autowired
    private ModuloRepository repository;
    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(Modulo modulo) {

        List<Lesson> lessonList = lessonRepository.findAllLessonsIntoModule(modulo.getModuleId());
        if (!lessonList.isEmpty()){
            lessonRepository.deleteAll(lessonList);
        }
        repository.deleteById(modulo.getModuleId());

    }
}
