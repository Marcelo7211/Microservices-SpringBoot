package com.ead.course.controller;

import com.ead.course.dto.CourseDto;
import com.ead.course.dto.LessonDto;
import com.ead.course.models.Course;
import com.ead.course.models.Lesson;
import com.ead.course.service.LessonService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/lesson")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    private LessonService service;

    @PostMapping
    public ResponseEntity<LessonDto> post(@RequestBody @Validated LessonDto courseDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDto));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonDto> put(@PathVariable(value = "lessonId") UUID lessonId,
                                         @RequestBody
                                         @Validated
                                         LessonDto lessonDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(lessonId, lessonDto));
    }


    @DeleteMapping("/{lessonId}")
    public ResponseEntity<?> delete( @PathVariable(value = "lessonId") UUID lessonId) {
        service.delete(lessonId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    };

    @GetMapping
    public ResponseEntity<Page<Lesson>> findAll(@And({
                                                        @Spec(path = "description", spec = Like.class),
                                                        @Spec(path = "title", spec = Like.class)
                                                    })
                                                Specification<Lesson> spec,
                                                @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
                                                Pageable pageable) {

        return ResponseEntity.ok(service.findAllPageable(spec, pageable));
    }


    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonDto> findById (@PathVariable(value = "lessonId") UUID lessonId) {
        var lessonDto = new LessonDto();

        BeanUtils.copyProperties(service.findById(lessonId), lessonDto);

        return ResponseEntity.ok(lessonDto);
    }


}
