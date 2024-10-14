package com.ead.course.controller;

import com.ead.course.dto.CourseDto;
import com.ead.course.models.Course;
import com.ead.course.service.CourseService;
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
@RequestMapping("/course")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    private CourseService service;

    @PostMapping
    public ResponseEntity<CourseDto> post(@RequestBody @Validated CourseDto courseDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDto));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDto> put(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Validated CourseDto courseDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(courseId, courseDto));
    }


    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> delete(@PathVariable(value = "courseId") UUID courseId) {
        service.delete(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    };

    @GetMapping
    public ResponseEntity<Page<Course>> findAll(@And({
                                                        @Spec(path = "courseLevel", spec = Equal.class),
                                                        @Spec(path = "courseStatus", spec = Equal.class),
                                                        @Spec(path = "name", spec = Like.class)
                                                    })
                                                    Specification<Course> spec,
                                                    @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
                                                    Pageable pageable) {

        return ResponseEntity.ok(service.findAllPageable(spec, pageable));
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> findById (@PathVariable(value = "courseId") UUID courseId) {
        var courseDto = new CourseDto();

        BeanUtils.copyProperties(service.findById(courseId), courseDto);

        return ResponseEntity.ok(courseDto);
    }

}
