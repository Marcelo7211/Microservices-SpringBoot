package com.ead.course.controller;

import com.ead.course.dto.CourseDto;
import com.ead.course.dto.ModuloDto;
import com.ead.course.models.Modulo;
import com.ead.course.service.ModuloService;
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
@RequestMapping("/module")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuloController {

    @Autowired
    public ModuloService service;

    @PostMapping
    public ResponseEntity<ModuloDto> post(@RequestBody @Validated ModuloDto moduloDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(moduloDto));
    }

    @PutMapping("/{moduloId}")
    public ResponseEntity<ModuloDto> put(@PathVariable(value = "moduloId") UUID moduloId, @RequestBody @Validated ModuloDto moduloDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(moduloId, moduloDto));
    }


    @DeleteMapping("/{moduloId}")
    public ResponseEntity<?> delete(@PathVariable(value = "moduloId") UUID moduloId) {
        service.delete(moduloId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    };

    @GetMapping
    public ResponseEntity<Page<Modulo>> findAll(@And({
                                                        @Spec(path = "description", spec = Like.class),
                                                        @Spec(path = "title", spec = Like.class)
                                                      })
                                                Specification<Modulo> spec,
                                                @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
                                                Pageable pageable) {

        return ResponseEntity.ok(service.findAllPageable(spec, pageable));
    }


    @GetMapping("/{moduloId}")
    public ResponseEntity<ModuloDto> findById (@PathVariable(value = "moduloId") UUID moduloId) {
        var moduloDto = new ModuloDto();

        BeanUtils.copyProperties(service.findById(moduloId), moduloDto);

        return ResponseEntity.ok(moduloDto);
    }
}
