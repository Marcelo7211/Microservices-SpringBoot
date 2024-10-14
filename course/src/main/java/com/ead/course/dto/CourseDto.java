package com.ead.course.dto;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseDto {

    @NotBlank(message = "O campo nao pode estar vazio ou nulo")
    private String name;

    @NotBlank(message = "O campo nao pode estar vazio ou nulo")
    private String description;

    @NotBlank(message = "O campo nao pode estar vazio ou nulo")
    private String imageUrl;

    @NotNull
    private CourseStatus courseStatus;

    @NotNull
    private UUID userInstrutor;

    @NotNull
    private CourseLevel courseLevel;


}
